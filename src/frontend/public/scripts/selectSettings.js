function setNone(className) {
    var cols = document.getElementsByClassName(className);
    for(i = 0; i < cols.length; i++) {
    cols[i].style.display = 'none';
    }
}
function setInline(className) {
    var cols = document.getElementsByClassName(className);
    for(i = 0; i < cols.length; i++) {
    cols[i].style.display = 'inline';
    }
}

function changedSelect(){
    let selectElement = document.querySelector("#selectOption");
    // Get the selected option using the value property of the select element
    let selectedValue = selectElement.value;
    console.log(selectedValue);
    switch (selectedValue) {

    case "email":
        setInline("email");
        setNone("password");
        setNone("fn");
        setNone("ln");
        break;
    case "password":
        setInline("password");
        setNone("email");
        setNone("fn");
        setNone("ln");
        break;
    case "fn":
        setInline("fn");
        setNone("password");
        setNone("email");
        setNone("ln");
        break;
    case "ln":
        setInline("ln");
        setNone("password");
        setNone("fn");
        setNone("email");
        break;
    
    default:
        break;
    }


}