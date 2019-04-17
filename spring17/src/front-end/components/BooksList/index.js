import React from 'react';
import {Link} from "react-router-dom";
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ListItemText from '@material-ui/core/ListItemText';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import Grid from '@material-ui/core/Grid';
import FolderIcon from '@material-ui/icons/Folder';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import CommentIcon from '@material-ui/icons/Comment';
import Paper from '@material-ui/core/Paper';
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

const BooksList = ({classes, list, onClick}) => (
        <div className={classes.root}>
            <Paper className={classes.paper}>
            <Grid item md={12}>
                <div className={classes.demo}>
                    <List >
                        {list.map(books=>(
                        <ListItem key={books.id}>
                            <ListItemAvatar>
                                <Avatar>
                                    <FolderIcon />
                                </Avatar>
                            </ListItemAvatar>
                            <ListItemText
                                primary={books.name}
                                secondary = {`${books.author.name} ${books.genre.name}`}
                            />
                            <ListItemSecondaryAction>
                                <Link to={`/reviews/${books.id}`}>
                                    <IconButton aria-label="Comment">
                                        <CommentIcon />
                                    </IconButton>
                                </Link>
                                <Link to={`/edit/${books.id}`}>
                                    <IconButton aria-label="Edit">
                                        <EditIcon />
                                    </IconButton>
                                </Link>
                                <IconButton aria-label="Delete" onClick={onClick(books.id)}>
                                    <DeleteIcon />
                                </IconButton>
                            </ListItemSecondaryAction>
                        </ListItem>))}
                    </List>
                    <Link to={'/add/'}>
                        <Button variant="contained" color="primary">Add book</Button>
                    </Link>
                </div>
            </Grid>
            </Paper>
        </div>
)

BooksList.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(BooksList);

