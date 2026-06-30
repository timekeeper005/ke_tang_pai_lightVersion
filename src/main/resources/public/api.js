const API = {

    AUTH: "/api/auth",

    USER: "/api/user",

    COURSE: "/api/course",

    ASSIGNMENT: "/api/assignment",

    SUBMISSION: "/api/submission"

};

async function apiGet(url) {

    const response = await fetch(url);

    return await response.json();

}

async function apiPost(url, data) {

    const response = await fetch(url, {

        method: "POST",

        headers: jsonHeaders(),

        body: JSON.stringify(data)

    });

    return await response.json();

}

async function apiPut(url, data) {

    const response = await fetch(url, {

        method: "PUT",

        headers: jsonHeaders(),

        body: JSON.stringify(data)

    });

    return await response.json();

}

async function apiDelete(url) {

    const response = await fetch(url, {

        method: "DELETE"

    });

    return await response.json();

}