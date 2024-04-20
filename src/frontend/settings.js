export function updateSettings(selectOption, bodyReq){
    let errors = []
    switch (selectOption) {
    case "email":
      if (bodyReq.newemail != bodyReq.newemail2){
        errors.push("Passwords don't match");
        console.log("passwords don't match");
        break;
      }
    case "password":
      if (bodyReq.newpassword != bodyReq.newpassword2){
        errors.push("Passwords don't match");
        console.log("passwords don't match");
        break;
      }
    case "fn":
      if (bodyReq.newfn != bodyReq.newfn2){
        errors.push("Passwords don't match");
        console.log("passwords don't match");
        break;
      }
    case "ln":
      if (bodyReq.newln != bodyReq.newln2){
        errors.push("Passwords don't match");
        console.log("passwords don't match");
        break;
      }
  
    default:
      break;
  }
  return errors;
}