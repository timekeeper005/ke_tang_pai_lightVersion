function getQuery(name){

    return new URLSearchParams(window.location.search).get(name);

}

function saveUser(user){

    localStorage.setItem(STORAGE.UID,user.uid);

    localStorage.setItem(STORAGE.ROLE,user.role);

    localStorage.setItem(STORAGE.NAME,user.name);

}

function currentUid(){

    return localStorage.getItem(STORAGE.UID);

}

function currentRole(){

    return localStorage.getItem(STORAGE.ROLE);

}

function logout(){

    localStorage.clear();

    window.location.href="../user/login.html";

}

function jsonHeaders(){

    return {

        "Content-Type":"application/json"

    };

}