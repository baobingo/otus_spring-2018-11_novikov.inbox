import React, { Component } from 'react';
import 'whatwg-fetch';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemText from '@material-ui/core/ListItemText';
import Avatar from '@material-ui/core/Avatar';
import Grid from '@material-ui/core/Grid';
import Paper from '@material-ui/core/Paper';
import CommentIcon from '@material-ui/icons/Comment';
import {Link} from "react-router-dom";
import Button from '@material-ui/core/Button';


const styles = theme => ({
    demo: {
        backgroundColor: theme.palette.background.paper,
    },
    title: {
        margin: `${theme.spacing.unit * 4}px 0 ${theme.spacing.unit * 2}px`,
    },
    root: {
        flexGrow: 1,
        overflow: 'hidden',
        padding: `0 ${theme.spacing.unit * 3}px`,
    },
    paper: {
        maxWidth: 600,
        margin: `${theme.spacing.unit}px auto`,
        padding: theme.spacing.unit * 2,
    }
});

class Reviews extends Component{
    state = {
        reviews:[]
    }

    componentDidMount() {
        const {id} = this.props.match.params;
        fetch(`http://localhost:8080/api/books/${id}/reviews`).then(response=>
        response.json()).then(json=>this.setState({reviews: json}));
    }

    render() {

        const { classes } = this.props;

        return(
            <div className={classes.root}>
                <Paper className={classes.paper}>
                    <Grid item md={12}>
                        <div className={classes.demo}>
                            <List >
                                {this.state.reviews.map(review=>(
                                    <ListItem key={review.reviewId}>
                                        <ListItemAvatar>
                                            <Avatar>
                                                <CommentIcon />
                                            </Avatar>
                                        </ListItemAvatar>
                                        {review.id}
                                        <ListItemText
                                            primary={review.body}
                                            secondary={review.author}
                                        />
                                    </ListItem>))}
                            </List>
                            <Link to={'/'}>
                                <Button variant="contained" color="primary">Back</Button>
                            </Link>
                        </div>
                    </Grid>
                </Paper>
            </div>
        )
    }
}

Reviews.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Reviews);