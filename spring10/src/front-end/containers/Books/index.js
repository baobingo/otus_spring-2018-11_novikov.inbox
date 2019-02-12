import React, { Component } from 'react';
import 'whatwg-fetch';
import BooksList from '../../components/BooksList'

class Books extends Component{

    constructor(props){
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }

    state = {
        books: [],
        update: false
    }

    handleClick = value => event => {
        fetch(`http://localhost:8080/api/book/delete/?id=${value}`);
        setTimeout(()=>this.fetchData(), 1000);
    };

    fetchData = () =>{
        fetch("http://localhost:8080/api/books").then(response=>response.json())
            .then(json=> this.setState({books: json}))
    }

    componentDidMount() {
        this.fetchData()

    }

    render() {
        return (
            <div><BooksList list = {this.state.books} onClick = {this.handleClick}/></div>
        );
    }
}

export default Books;