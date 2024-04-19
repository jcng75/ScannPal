const form = document.getElementById("login-form");
const email = document.getElementById("email-input");
const password = document.getElementById("password-input");

form.addEventListener("submit", e => {
    e.preventDefault();

    validateInputs();
});

const setError = (element, message) => {
    // get the parent of the element (which is the input-section div)
    const inputSection = element.parentElement.parentElement;
    
    // get the error div
    const errorDisplay = inputSection.querySelector(".error");

    errorDisplay.innerText = message;
    inputSection.classList.add("error");
    inputSection.classList.remove("success");
};

const setSuccess = (element) => {
    const inputSection = element.parentElement.parentElement;
    const errorDisplay = inputSection.querySelector(".error");

    errorDisplay.innerText = "";
    inputSection.classList.add("success");
    inputSection.classList.remove("error");
};

const validateInputs = () => {
    const emailValue = email.value.trim();
    const passwordValue = password.value.trim();
    let isValid = true;

    if (emailValue === "") {
        setError(email, "Email is required");
        isValid = false;
    } else {
        setSuccess(email);
    }

    if (passwordValue === "") {
        setError(password, "Password is required");
        isValid = false;
    } else {
        setSuccess(password);
    }

    if (isValid) {
        form.submit();
    }
}