import {
    Route,
    Routes
} from "react-router-dom"
import Home from "../views/Home";
import PlayersView from "../views/PlayersView";
import PlayerEdit from "../views/PlayerEdit";


const DefaultRoute = "/"

const Router = () => {

    return (
        <Routes>
            <Route path="/players/:playerId/edit"
            element={<PlayerEdit />}/>

            <Route path="/players"
                element={<PlayersView />}/>

            <Route path="/"
                   element={<Home />} />
        </Routes>
    )
}

export default Router