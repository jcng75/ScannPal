const form = document.getElementById("register-form");
const firstName = document.getElementById("firstname-input");
const lastName = document.getElementById("lastname-input");
const email = document.getElementById("email-input");
const password = document.getElementById("password-input");
const passwordConfirm = document.getElementById("password-confirm");

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

const isValidEmail = (email) => {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
};

const isValidPassword = (password) => {
    const hasUppercase = /[A-Z]/.test(password);
    const hasLowercase = /[a-z]/.test(password);
    const hasDigit = /\d/.test(password);
    const hasSpecialChar = /[^A-Za-z0-9\s]/.test(password);
    const hasValidLength = password.length >= 12

    // return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    if (!hasValidLength) {
        return "Requires at least 12 characters";
    }

    const missingRequirements = [];
    
    if (!hasUppercase) {
        missingRequirements.push("1 uppercase character");
    }
    if (!hasLowercase) {
        missingRequirements.push("1 lowercase character");
    }
    if (!hasDigit) {
        missingRequirements.push("1 number");
    }
    if (!hasSpecialChar) {
        missingRequirements.push("1 special character");
    }

    if (missingRequirements.length > 0) {
        let errors = "Password requires at least ";
        if (missingRequirements.length === 1) {
            errors += missingRequirements[0];
        } 
        else if (missingRequirements.length === 2) {
            errors += `${missingRequirements[0]} and ${missingRequirements[1]}`;
        } 
        else {
            for (let i = 0; i < missingRequirements.length; i++) {
                if (i < missingRequirements.length - 1) {
                    errors += `${missingRequirements[i]}, `;
                } else {
                    errors += `and ${missingRequirements[i]}`;
                }
            }
        }
        return errors;
    }

    return "valid password";
};

const validateInputs = () => {
    const firstNameValue = firstName.value.trim();
    const lastNameValue = lastName.value.trim();
    const emailValue = email.value.trim();
    const passwordValue = password.value.trim();
    const passwordConfirmValue = passwordConfirm.value.trim();

    let isValid = true;

    if (firstNameValue === "") {
        setError(firstName, "First name is required");
        isValid = false;
    } else {
        setSuccess(firstName);
    }

    if (lastNameValue === "") {
        setError(lastName, "Last name is required");
        isValid = false;
    } else {
        setSuccess(lastName);
    }

    if (emailValue === "") {
        setError(email, "Email is required");
        isValid = false;
    } else if (!isValidEmail(emailValue)) { // not a valid email
        setError(email, "Not a valid email");
        isValid = false;
    } else {
        setSuccess(email);
    }

    const errors = isValidPassword(passwordValue);
    if (passwordValue === "") {
        setError(password, "Password is required");
        isValid = false;
    } else if (passwordValue.length < 12) {
        setError(password, "Password must contain at least 12 characters");
        isValid = false;
    } else if(errors !== "valid password") {
        setError(password, errors);
        isValid = false;
    } else {
        setSuccess(password);
    }

    if (passwordConfirmValue === "") {
        setError(passwordConfirm, "Please confirm your password");
        isValid = false;
    } else if (passwordValue !== passwordConfirmValue) {
        setError(passwordConfirm, "Passwords do not match");
        isValid = false;
    } else {
        setSuccess(passwordConfirm);
    }

    if (isValid) {
        form.submit();
    }
};