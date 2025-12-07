document.getElementById("loginForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    ["email", "password"].forEach(id => {
        const el = document.getElementById("error-" + id);
        if (el) {
            el.style.display = "none";
            el.querySelector("span").innerText = "";
        }
        document.getElementById(id).classList.remove("is-invalid");
    });

    const payload = {
        email: document.getElementById("email").value.trim(),
        password: document.getElementById("password").value.trim()
    };

    try {
        const res = await fetch("http://localhost:8081/userApi/v1/login", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(payload)
        });

        if (res.ok) {
            const userRes = await fetch(`http://localhost:8081/userApi/v1/user/email?email=${payload.email}`);
            const userData = await userRes.json();
            localStorage.setItem("user", JSON.stringify(userData));

            // Redirect based on role
            if (userData.role && userData.role.toUpperCase() === "ADMIN") {
                window.location.href = "/admin-dashboard.html";
            } else {
                window.location.href = "/books.html";
            }
            return;
        }

        const data = await res.json();
        const errors = data.errorMessages || data.errors || {};

        if (errors["Error"]) {
            const el = document.getElementById("error-password");
            el.querySelector("span").innerText = errors["Error"];
            el.style.display = "block";
            document.getElementById("password").classList.add("is-invalid");
        }

        for (let key in errors) {
            const el = document.getElementById("error-" + key.toLowerCase());
            if (el) {
                el.querySelector("span").innerText = errors[key];
                el.style.display = "block";
                document.getElementById(key.toLowerCase()).classList.add("is-invalid");
            }
        }

    } catch (err) {
        alert("Server Error. Please try again.");
        console.error(err);
    }
});
