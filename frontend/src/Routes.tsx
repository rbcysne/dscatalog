import React from "react";
import { BrowserRouter, Switch, Route } from "react-router-dom";

import Navbar from "components/Navbar";
import Home from "pages/Home";
import Catalog from "pages/Catalog";
import Admin from "pages/Admin";

const Routes = () => {
    return (
        <BrowserRouter>
            <Navbar />
            <Switch>
                <Route component = { Home } path="/" exact />

                <Route component ={ Catalog } path="/catalog" />

                <Route component ={ Admin } path="/admin" />
            </Switch>
        </BrowserRouter>
    );
}

export default Routes;