document.getElementById("registerForm").addEventListener("submit", async function(e){
    e.preventDefault();

    ["firstName","lastName","email","password","confirmPassword","role"].forEach(id => {
        document.getElementById("error-" + id).innerText = "";
    });

    const payload = {
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value,
        confirmPassword: document.getElementById("confirmPassword").value,
        role: document.getElementById("role").value
    };

    try {
        const res = await fetch("http://localhost:8081/userApi/v1/registration", {
            method: "POST",
            headers: {"Content-Type":"application/json"},
            body: JSON.stringify(payload)
        });
        const data = await res.json();

        if(res.ok){
            window.location.href = "/login.html";
        } else {
            for(let key in data.errorMessages){
                const el = document.getElementById("error-" + key);
                if(el) el.innerText = data.errorMessages[key];
            }
        }
    } catch(err){
        console.error("Server Error: ", err);
    }
});
