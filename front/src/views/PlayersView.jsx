import {Link} from "react-router-dom";
import DataTable from 'react-data-table-component';
import {useEffect, useState} from "react";
import apiService from "../services/ApiService";
import Navbar from "./Navbar";

const columns = [
    {
        name: "Player ID",
        selector: row => row.id
    },
    {
        name: "Nickname",
        selector: row => row.nickname
    },
    {
        name: "UUID",
        selector: row => row.uuid
    },
    {
        button: true,
        cell: (row) => {
            return (
                <div>
                    <Link to={`/players/${row.id}/edit`}>Edit</Link>

                </div>
            )
        }
    }
]

const PlayersView = () => {

    const [players, setPlayers] = useState(null)
    const [loading, setLoading] = useState(false)

    useEffect(() => {

        apiService.getAllPlayers().then((req) => {

            if (req.request.status >= 200 && req.request.status < 300) {

                if (req.data) {
                    setPlayers(req.data)
                    setLoading(true)
                }
            } else {
                console.error("Something happened with request!")
            }
        })
    }, [])

    return (
        <div>
            <Navbar />
            {loading ? (
                <DataTable columns={columns}
                           data={players}
                           noHeader={true}
                           onSelectedRowsChange={(data) => {console.log(data)}}
                           selectableRows={true}
                />
            ) : null}
        </div>
    )
}

export default PlayersView