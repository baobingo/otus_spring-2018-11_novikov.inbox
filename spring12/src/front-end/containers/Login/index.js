import React, { Component } from 'react';
import 'whatwg-fetch';
import { Redirect } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Cookies from 'universal-cookie';

const styles = theme => ({
    container: {
        display: 'flex',
        flexWrap: 'wrap',
        width: 300
    },
    textField: {
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
        width: 300,
    },
    dense: {
        marginTop: 19,
    },
    root: {
        flexGrow: 1,
        overflow: 'hidden',
        padding: `0 ${theme.spacing.unit * 3}px`,
    },
    paper: {
        maxWidth: 300,
        margin: `${theme.spacing.unit}px auto`,
        padding: theme.spacing.unit * 2,
    }
});

const cookies = new Cookies();

class Login extends Component{

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            authenticated: false,
            message: ''
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }


    componentDidMount() {
        if(cookies.get('auth') == 1){
            this.setState({authenticated: true})
        }
    }

    handleChange = name => event => {
        this.setState({ [name]: event.target.value });
    };

    handleSubmit(e){
        const formData = new FormData(document.querySelector('form'));
        const data = new URLSearchParams();

        for (const [key, value]  of formData.entries()) {
            data.append(key, value);
        }
        fetch('http://localhost:8080/login', {
            method: 'POST',
            body: data.toString(),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).then(response => {
            if (response.status == 200) {
                cookies.set('auth', 1)
                this.setState({authenticated: true})
                window.location.reload();
            }else{
                cookies.remove('auth')
                this.setState({authenticated: false})
                this.setState({message: 'Wrong credentials'})
            }
        });

        e.preventDefault();
    }

    render() {

        const { classes } = this.props;

        return(
            <div>
                {this.state.message && (<h3>{this.state.message}</h3>)}
                {!this.state.authenticated &&
                (<div className={classes.root}>
                    <Paper className={classes.paper}>
                        <Grid container wrap="nowrap" spacing={16}>
                            <Grid item>
                                <form className={classes.container} onSubmit={this.handleSubmit}>
                                    <TextField
                                        required
                                        id="username"
                                        className={classes.textField}
                                        name="username"
                                        label="Username"
                                        onChange={this.handleChange('username')}
                                        margin="normal"

                                    />
                                    <TextField
                                        required
                                        id="password"
                                        className={classes.textField}
                                        name="password"
                                        label="Password"
                                        type="password"
                                        onChange={this.handleChange('password')}
                                        margin="normal"
                                    />
                                    <Button className={classes.textField} type="submit" variant="contained" color="primary">Login</Button>
                                </form>
                            </Grid>
                        </Grid>
                    </Paper>
                    <h5>user/password - allow GET, PUT, POST</h5>
                    <h5>admin/password - allow DELETE</h5>
                </div>)}

            </div>
        )
    }
}

Login.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Login);