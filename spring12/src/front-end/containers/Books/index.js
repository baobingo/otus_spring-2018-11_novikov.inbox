import React, { Component } from 'react';
import BooksList from '../../components/BooksList'
import Login from '../Login'
import Cookies from 'universal-cookie';
import Button from '@material-ui/core/Button';
import PropTypes from "prop-types";
import {withStyles} from "@material-ui/core";
import {fetchDelete, fetchGet} from "../../Services/fetchUtil"

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
        fetchDelete(value).then(response => {
            if(response.status == 403){
                this.setState({message: 'Denied. Log in as ADMIN'})
            }
        }).then(()=> setTimeout(()=>this.fetchData(), 1000));
    };

    fetchData = () =>{
        if(cookies.get('auth') == 1){
            this.setState({authenticated: true})
        }else{
            this.setState({authenticated: false})
        }
        fetchGet().then(response => {
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
        const { message, authenticated, books } = this.state;

        return (
            <div className={classes.logout}>
                {message && (<h3>{message}</h3>)}
                {authenticated && (<BooksList list = {books} onClick = {this.handleClick}/>)}
                <Login/>
                {authenticated && (<Button variant="contained" color="primary" onClick = {this.logourHandler}>Logout</Button>)}
            </div>

        );
    }
}

Books.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Books)