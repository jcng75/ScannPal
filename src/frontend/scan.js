import { exec } from 'child_process';

export function checkEmpty(bodyReq){
  let errorString = 'The following fields are empty: ';
  let currLength = errorString.length;
  if (bodyReq.website === ""){
    errorString += 'Website; ';
  }
  if (bodyReq.username === ""){
    errorString += 'Username; ';
  }
  if (bodyReq.usernameTag === ""){
    errorString += 'UsernameTag; ';
  }
  if (bodyReq.password === ""){
    errorString += 'Password; ';
  }
  if (bodyReq.passwordTag === ""){
    errorString += 'PasswordTag; ';
  }
  if (bodyReq.depth === ""){
    errorString += 'Depth; ';
  }
  if (errorString.length > currLength) {
    return errorString;
  }
  return null;
}

export function runChecks(bodyReq){
  let errors = [];

  let emptyString = checkEmpty(bodyReq);
  if (emptyString) {
    errors.push(emptyString);
  }
  // if (!canGetHttp(bodyReq.website)){
  //   errors.push('Invalid URL Provided');
  // }

  return errors;
}

export function runScan(bodyReq, email){
  exec(`cd ../../; sudo java -jar ScannPalMainNode.jar ${bodyReq.website} ${bodyReq.username} ${bodyReq.password} ${bodyReq.usernameTag} ${bodyReq.passwordTag} ${bodyReq.depth} ${email}`, (err, stdout, stderr) => {
    if (err) {
      // node couldn't execute the command
      console.error(err);
      return;
    }

    // the *entire* stdout and stderr (buffered)
    console.log(`stdout: ${stdout}`);
    console.log(`stderr: ${stderr}`);
  });
}