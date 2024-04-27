import { curly } from 'node-libcurl';

async function canGetHttp(url){

  const { statusCode, data, headers } = await curly.get(url);
  console.log(data);
  if (statusCode === 200){
    return true;
  }
  return false;

}

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
  if (!canGetHttp){
    errors.push('Invalid URL Provided');
  }

  return errors;
}

