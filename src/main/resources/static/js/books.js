// ===== Get logged-in user =====
const user = JSON.parse(localStorage.getItem("user"));
if (!user) {
    window.location.href = "/login.html";
}
const userEmail = user.email;

let currentPage = 0;
let totalPages = 1;
let currentCategory = "";
let currentTitle = "";

// ===== Load Books =====
async function loadBooks(page = 0) {
    try {
        let url = `http://localhost:8081/bookApi/v1/books?page=${page}&size=4&email=${encodeURIComponent(userEmail)}`;

        if (currentTitle && currentCategory) {
            url = `http://localhost:8081/bookApi/v1/books/search/title?title=${encodeURIComponent(currentTitle)}&page=${page}&size=4&email=${encodeURIComponent(userEmail)}`;
            const res = await fetch(url);
            const data = await res.json();
            const books = data.content.filter(b => b.category === currentCategory);
            renderBooks(books);
            currentPage = data.number;
            totalPages = data.totalPages;
        } else if (currentTitle) {
            url = `http://localhost:8081/bookApi/v1/books/search/title?title=${encodeURIComponent(currentTitle)}&page=${page}&size=4&email=${encodeURIComponent(userEmail)}`;
            const res = await fetch(url);
            const data = await res.json();
            renderBooks(data.content);
            currentPage = data.number;
            totalPages = data.totalPages;
        } else if (currentCategory) {
            url = `http://localhost:8081/bookApi/v1/books/search/category?category=${encodeURIComponent(currentCategory)}&page=${page}&size=4&email=${encodeURIComponent(userEmail)}`;
            const res = await fetch(url);
            const data = await res.json();
            renderBooks(data.content);
            currentPage = data.number;
            totalPages = data.totalPages;
        } else {
            const res = await fetch(url);
            const data = await res.json();
            renderBooks(data.content);
            currentPage = data.number;
            totalPages = data.totalPages;
        }

        renderPagination();
    } catch (err) {
        console.error(err);
        document.getElementById("booksContainer").innerHTML =
            `<h3 class="text-white text-center">⚠ Error loading books</h3>`;
    }
}

// ===== Render Books =====
function renderBooks(books) {
    const container = document.getElementById("booksContainer");
    container.innerHTML = "";

    if (!books || books.length === 0) {
        container.innerHTML = `<h3 class="text-white text-center">No books found</h3>`;
        return;
    }

    books.forEach(book => {
        let imageSrc = "/images/default-book.png"; // default
        if (book.coverImg) {
            if (book.coverImg.startsWith("data:")) {
                imageSrc = book.coverImg; // already base64
            } else {
                imageSrc = book.coverImg; // url path
            }
        }

        const card = document.createElement("div");
        card.className = "col-md-3 mb-4";
        card.innerHTML = `
            <div class="card h-100">
                <img src="${imageSrc}" class="card-img-top" alt="${book.title}">
                <div class="card-body">
                    <h5 class="card-title">${book.title}</h5>
                    <p class="card-text author">By ${book.author}</p>
                    <span class="badge badge-cat">${book.category}</span>
                    <div class="card-price mt-2 mb-2">$${book.price}</div>
                    <button class="btn-like ${book.liked ? 'liked' : ''}" 
                        data-id="${book.id}" 
                        data-liked="${book.liked ? 'true' : 'false'}">
                        <i class="bi bi-heart-fill"></i> <span class="like-count">${book.likesCount}</span>
                    </button>
                </div>
            </div>
        `;
        container.appendChild(card);
    });
}

// ===== Pagination =====
function renderPagination() {
    const pagination = document.getElementById("pagination");
    pagination.innerHTML = "";

    const createPageBtn = (text, page, disabled = false, active = false) => {
        const li = document.createElement("li");
        li.className = `page-item ${disabled ? "disabled" : ""} ${active ? "active" : ""}`;
        const a = document.createElement("a");
        a.className = "page-link";
        a.href = "#";
        a.textContent = text;
        a.addEventListener("click", (e) => {
            e.preventDefault();
            if (!disabled) loadBooks(page);
        });
        li.appendChild(a);
        return li;
    };

    // Prev
    pagination.appendChild(createPageBtn("<", currentPage - 1, currentPage === 0));
    // Pages
    for (let i = 0; i < totalPages; i++) {
        pagination.appendChild(createPageBtn(i + 1, i, false, currentPage === i));
    }
    // Next
    pagination.appendChild(createPageBtn(">", currentPage + 1, currentPage === totalPages - 1));
}

// ===== Search & Filter =====
document.getElementById("searchBtn").addEventListener("click", () => {
    currentTitle = document.getElementById("searchInput").value.trim();
    currentCategory = document.getElementById("categoryFilter").value;
    loadBooks(0);
});

// ===== Like Toggle =====
document.addEventListener("click", async (e) => {
    const btn = e.target.closest(".btn-like");
    if (!btn) return;

    const bookId = btn.dataset.id;
    const likeCountSpan = btn.querySelector(".like-count");
    let isLiked = btn.dataset.liked === "true";

    let count = parseInt(likeCountSpan.textContent);
    count = isLiked ? count - 1 : count + 1;
    likeCountSpan.textContent = count;

    isLiked = !isLiked;
    btn.dataset.liked = isLiked.toString();
    btn.classList.toggle("liked", isLiked);

    // backend
    try {
        const method = isLiked ? "POST" : "DELETE";
        const res = await fetch(`http://localhost:8081/bookApi/v1/books/like?bookId=${bookId}&email=${encodeURIComponent(userEmail)}`, { method });
        const data = await res.json();

        likeCountSpan.textContent = data.likesCount;
        btn.dataset.liked = data.liked ? "true" : "false";
        btn.classList.toggle("liked", data.liked);
    } catch (err) {
        console.error(err);
    }
});

// ===== Initial Load =====
document.addEventListener("DOMContentLoaded", () => loadBooks());
