import React, { Component } from 'react';
import 'whatwg-fetch';
import { Redirect } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';

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

class FormAdd extends Component{

    constructor(props) {
        super(props);
        this.state = {
            id: '',
            name: '',
            author: '',
            genre: '',
            redirect: false
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }


    componentDidMount() {
    }

    handleChange = name => event => {
        this.setState({ [name]: event.target.value });
    };

    handleSubmit(e){
        var form = document.querySelector('form')

        fetch('http://localhost:8080/api/books', {
            method: 'POST',
            body: new FormData(form)
        })

        e.preventDefault();
        setTimeout(()=>(this.setState({redirect: true})), 1000)

    }

    render() {

        const { classes } = this.props;

        return(
            <div className={classes.root}>
                <Paper className={classes.paper}>
                    <Grid container wrap="nowrap" spacing={16}>
                        <Grid item>
                            <form className={classes.container} onSubmit={this.handleSubmit}>
                                <TextField
                                    id="name"
                                    className={classes.textField}
                                    name="name"
                                    label="Name"
                                    onChange={this.handleChange('name')}
                                    margin="normal"
                                />
                                <TextField
                                    id="author"
                                    className={classes.textField}
                                    name="author"
                                    label="Author"
                                    onChange={this.handleChange('name')}
                                    margin="normal"
                                />
                                <TextField
                                    id="genre"
                                    className={classes.textField}
                                    name="genre"
                                    label="Genre"
                                    onChange={this.handleChange('name')}
                                    margin="normal"
                                />
                                <Button className={classes.textField} type="submit" variant="contained" color="primary">Add</Button>
                            </form>
                        </Grid>
                    </Grid>
                </Paper>
                {this.state.redirect &&
                (<Redirect to = '/'/>)
                }
            </div>
        )
    }
}

FormAdd.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(FormAdd);