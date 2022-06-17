
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Navbar from "components/Navbar";
import Home from "pages/Home";
import Catalog from "pages/Catalog";
import Admin from "pages/Admin";

const Routes = () => {
    return (
        <Router>
            <Navbar />
            <Switch>
                <Route component = { Home } path="/" exact />

                <Route component ={ Catalog } path="/catalog" />

                <Route component ={ Admin } path="/admin" />
            </Switch>
        </Router>
    );
}

export default Routes;