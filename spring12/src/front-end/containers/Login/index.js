import React, { Component } from 'react';
import 'whatwg-fetch';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import Cookies from 'universal-cookie';
import {loginPost} from "../../Services/fetchLogin";

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

        this.handleSubmit = this.handleSubmit.bind(this);
    }


    componentDidMount() {
        if(cookies.get('auth') == 1){
            this.setState({authenticated: true})
        }
    }

    handleSubmit(e){
        const formData = new FormData(document.querySelector('form'));
        const data = new URLSearchParams();

        for (const [key, value]  of formData.entries()) {
            data.append(key, value);
        }
        loginPost(data).then(response => {
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
        const { message, authenticated} = this.state;

        return(
            <div>
                {message && (<h3>{message}</h3>)}
                {!authenticated &&
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
                                        margin="normal"

                                    />
                                    <TextField
                                        required
                                        id="password"
                                        className={classes.textField}
                                        name="password"
                                        label="Password"
                                        type="password"
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