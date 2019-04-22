import React from 'react';
import { Route, Switch } from 'react-router-dom';
import Books from '../../containers/Books';
import Reviews from '../../containers/Reviews'
import FormEdit from "../../containers/FormEdit";
import FormAdd from "../../containers/FormAdd";

const Main = props => (
    <Switch>
        <Route exact path="/" component={Books}></Route>
        <Route path="/reviews/:id" component={Reviews}></Route>
        <Route path="/edit/:id" component={FormEdit}></Route>
        <Route path="/add/" component={FormAdd}></Route>
    </Switch>
)

export default Main;