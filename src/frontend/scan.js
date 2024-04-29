import { execFile } from 'child_process';
import { stderr, stdout } from 'process';

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
  const command = 'sudo';
  const args = ['java', '-jar', 'ScannPalMainNode.jar', bodyReq.website, bodyReq.username, bodyReq.password, bodyReq.usernameTag, bodyReq.passwordTag, bodyReq.depth, email]
  const options = {
    cwd: '../../'
  };
  return new Promise(resolve => {
    execFile(command, args, options, (err, stdout, stderr) => {
      let returnValue;
      if (err) {
        // node couldn't execute the command
        console.error(err);
        returnValue = true;
      } else {
        returnValue = false;
      }
  
      // the *entire* stdout and stderr (buffered)
      console.log(`stdout: ${stdout}`);
      // console.log(`stderr: ${stderr}`);
      resolve(returnValue);
    })
  });
}