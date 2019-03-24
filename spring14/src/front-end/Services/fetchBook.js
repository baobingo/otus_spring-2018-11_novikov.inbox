import 'whatwg-fetch';

export const fetchDelete = value => (
    fetch(`/api/books/${value}`, {
        method: 'DELETE',
    })
)

export const fetchGet = () => fetch("/api/books");

export const fetchPost = (jsonObject) => fetch('/api/books', {
    method: 'POST',
    body: JSON.stringify(jsonObject),
    headers: {
        'Content-Type': 'application/json'
    }
});

export const fetchPut = (jsonObject) =>
    fetch('/api/books', {
        method: 'PUT',
        body: JSON.stringify(jsonObject),
        headers: {
            'Content-Type': 'application/json'
        }
    });

export const fetchGetSingle = (id) => fetch(`/api/books/${id}`);

export const fetchGetReviews = (id) => fetch(`/api/books/${id}/reviews`);