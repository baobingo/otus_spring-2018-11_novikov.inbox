import React, { Component } from 'react';
import './index.css';
import Main from '../Main'
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';

const styles = theme => ({
    root: {
        flexGrow: 1,
        maxWidth: 600,
        overflow: 'hidden',
        margin: `${theme.spacing.unit}px auto`,
    }
});

class App extends Component {

    render() {
        const { classes } = this.props;
        return (
            <div className={classes.root}>
                <AppBar position="static" color="default">
                    <Toolbar>
                        <Typography variant="h6" color="inherit">
                            Simple Book Repo
                        </Typography>
                    </Toolbar>
                </AppBar>
                <div><Main /></div>
            </div>
        );
  }
}

App.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(App);