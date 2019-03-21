import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import { fetchPut, fetchGetSingle } from "../../Services/fetchBook";

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

class FormEdit extends Component{

    constructor(props) {
        super(props);
        this.state = {
            redirect: false,
            json: {},
            fetched: false
        }

        this.handleSubmit = this.handleSubmit.bind(this);
    }


    componentDidMount() {
        const {id} = this.props.match.params;

        fetchGetSingle(id).then(response=>
            response.json()).then(json => this.setState({json}, this.changeFetched));
    }

    changeFetched = () => (this.setState({fetched: true}))

    handleSubmit(e){

        const formData = new FormData(document.querySelector('form'));
        let jsonObject = {};

        for (const [key, value]  of formData.entries()) {
            jsonObject[key] = value;
        }
        e.preventDefault();

        fetchPut(jsonObject).then(() => this.setState({redirect: true}));

    }

    render() {

        const { classes } = this.props;
        const { json, redirect, fetched } = this.state;

        return(
            <div className={classes.root}>
                {fetched &&
                <Paper className={classes.paper}>
                    <Grid container wrap="nowrap" spacing={16}>
                        <Grid item>
                            <form className={classes.container} onSubmit={this.handleSubmit}>
                                <input type="text" name="id" defaultValue={json.id} hidden/>
                                <TextField
                                    id="name"
                                    className={classes.textField}
                                    name="name"
                                    label="Name"
                                    defaultValue={json.name}
                                    margin="normal"
                                />
                                <TextField
                                    id="author"
                                    className={classes.textField}
                                    name="author"
                                    label="Author"
                                    defaultValue={json.author.name}
                                    margin="normal"
                                />
                                <TextField
                                    id="genre"
                                    className={classes.textField}
                                    name="genre"
                                    label="Genre"
                                    defaultValue={json.genre.name}
                                    margin="normal"
                                />
                                <Button className={classes.textField} type="submit" variant="contained" color="primary">Update</Button>
                            </form>
                        </Grid>
                    </Grid>
                </Paper>}
                {redirect && (<Redirect to = '/'/>)}
            </div>
        )
    }
}

FormEdit.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(FormEdit);