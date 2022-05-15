import {Link} from "react-router-dom";
import {Button} from "reactstrap";
import apiService from "../services/ApiService";


const Navbar = () => {


    const handleDeletePlayers = () => {
        apiService.deleteAllPlayers().then((req) => {
            if (req.request.status >= 200 && req.request.status < 300) {

            } else {
                console.error("error happened in deleting players")
            }
        })
    }

    const handleDeleteProfiles = () => {
        apiService.deleteAllProfiles().then((req) => {
            if (req.request.status >= 200 && req.request.status < 300) {

            } else {
                console.error("error happened in deleting players")
            }
        })
    }

    return (
        <div>
            <Link to="/">Menu</Link>
            <Link to="/players">Players</Link>
            <Button color="primary" onClick={handleDeletePlayers}>Delete all players</Button>
            <Button color="primary" onClick={handleDeleteProfiles}>Delete all profiles</Button>
        </div>
    )
}

export default Navbar