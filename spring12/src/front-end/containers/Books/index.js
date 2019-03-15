import React, { Component } from 'react';
import 'whatwg-fetch';
import BooksList from '../../components/BooksList'
import Login from '../Login'
import Cookies from 'universal-cookie';
import Button from '@material-ui/core/Button';
import PropTypes from "prop-types";
import {withStyles} from "@material-ui/core";

const cookies = new Cookies();

const styles = theme => ({
    logout: {
        textAlign: 'center'
    }
});

class Books extends Component{

    constructor(props){
        super(props);
        this.handleClick = this.handleClick.bind(this);
        this.fetchData = this.fetchData.bind(this);
    }

    state = {
        books: [],
        update: false,
        authenticated: false,
        message: ''
    }

    handleClick = value => event => {
        fetch(`http://localhost:8080/api/books/${value}`, {
            method: 'DELETE',
        }).then(response => {
            if(response.status == 403){
                this.setState({message: 'Denied. Log in as ADMIN'})
            }
        })
        setTimeout(()=>this.fetchData(), 1000);
    };

    fetchData = () =>{
            if(cookies.get('auth') == 1){
                this.setState({authenticated: true})
            }else{
                this.setState({authenticated: false})
            }
            fetch("http://localhost:8080/api/books").then(response => {
                if(response.ok){
                    return response.json()
                }else{
                    cookies.remove('auth')
                    this.setState({authenticated: false})
                    throw new Error('Something went wrong');
                }
            }).then(json => this.setState({books: json})
            ).catch(e => console.log(e));
    }

    logourHandler = () => {
        cookies.remove('auth')
        window.location.reload()
    }

    componentDidMount() {
        this.fetchData()
    }


    render() {
        const { classes } = this.props;

        return (
            <div className={classes.logout}>
                {this.state.message && (<h3>{this.state.message}</h3>)}
                {this.state.authenticated && (<BooksList list = {this.state.books} onClick = {this.handleClick}/>)}
                <Login/>
                {this.state.authenticated && (<Button variant="contained" color="primary" onClick = {this.logourHandler}>Logout</Button>)}
            </div>

        );
    }
}

Books.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Books)