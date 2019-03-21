import "whatwg-fetch";

export const loginPost = (data) => fetch('/login', {
    method: 'POST',
    body: data.toString(),
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
    }
});