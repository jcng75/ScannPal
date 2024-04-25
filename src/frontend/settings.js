import { validateHash, updateEmail, updateFirstName, updateLastName, updatePassword } from "./database.js";

export async function updateSettings(selectOption, bodyReq, userId){
  switch (selectOption) {
    case "email":
      updateEmail(userId, bodyReq.newemail); 
      break;
    case "password":
      updatePassword(userId, bodyReq.newpassword); 
      break;
    case "fn":
      updateFirstName(userId, bodyReq.newfn); 
      break;
    case "ln":
      updateLastName(userId, bodyReq.newln);
    default:
      break;
  }
  
}

export async function verifySettings(selectOption, bodyReq, userData){
    let errors = []
    switch (selectOption) {
    case "email":
      if (bodyReq.newemail != bodyReq.newemail2){
        errors.push("New emails don't match.");
      }
      if (bodyReq.curremail.trim() != userData.email){
        errors.push("Please enter the correct current email.");
      }
      if (!isValidEmail(bodyReq.newemail.trim())){
        errors.push("Please enter a valid new email.")
      }
      break;
    case "password":
      if (bodyReq.newpassword != bodyReq.newpassword2){
        errors.push("New passwords don't match");
      }
      const isCorrectPassword = await validateHash(userData.email, bodyReq.currpassword.trim());
      if (!isCorrectPassword){
        errors.push("Please enter the correct current password.");
      }
      const passwordErrors = isValidPassword(bodyReq.newpassword.trim());
      if (passwordErrors !== "valid password"){
        errors.push(`New password does not meet the requirements: ${passwordErrors}`);
      }
      // Add check password
      break;
    case "fn":
      if (bodyReq.newfn != bodyReq.newfn2){
        errors.push("New first names don't match.");
      }
      if (bodyReq.currfn.trim() != userData.fname){
        errors.push("Please enter the correct current first name.");
      }
      break;
    case "ln":
      if (bodyReq.newln != bodyReq.newln2){
        errors.push("New last names don't match.");
      }
      if (bodyReq.currln.trim() != userData.lname){
        errors.push("Please enter the correct current last name.");
      }
    default:
      break;
  }
  return errors;
}

const isValidEmail = (email) => {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
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
