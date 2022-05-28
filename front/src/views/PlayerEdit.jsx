import {useEffect, useState} from "react";
import apiService from "../services/ApiService";
import {Link, useParams} from "react-router-dom";
import DataTable from 'react-data-table-component';
import 'bootstrap/dist/css/bootstrap.min.css';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {
    Button,
    Card, CardBody,
    Form,
    FormGroup,
    Modal,
    ModalBody,
    ModalFooter,
    ModalHeader,
    UncontrolledCollapse
} from "reactstrap";
import Navbar from "./Navbar";
import {toast, ToastContainer} from "react-toastify";

const PlayerEdit = () => {

    const [playerWithProfiles, setPlayerWithProfiles] = useState(null)
    const [loading, setLoading] = useState(false)
    const [profileLoading, setProfileLoading] = useState(false)
    const [toggleModal, setToggleModal] = useState(false)
    const [toggleDifferenceModal, setDifferenceModal] = useState(false)
    const [profileInfo, setProfileInfo] = useState(null)
    const [isDateSet, setIsDateSet] = useState(false)
    const [date, setDate] = useState(new Date())
    const [dateFrom, setDateFrom] = useState(new Date())
    const [dateTo, setDateTo] = useState(new Date())
    const playerId = useParams().playerId

    const profileColumns = [
        {
            name: "Profile Id",
            selector: row => row.id
        },
        {
            name: "hpId",
            selector: row => row.hpId
        },
        {
            name: "Title",
            selector: row => row.title
        },
        {
            button: true,
            cell: (row) => {
                return (
                    <Button color="primary" onClick={() => {
                        handleCurrentProfileModalTrigger(row.hpId)
                    }}>Current Info</Button>
                )
            }
        },
        {
            button: true,
            cell: (row) => {
                return (
                    <Button color="primary" onClick={() => {
                        handleProfileByDateModalTrigger(row.hpId)
                    }}>Info by Date</Button>
                )
            }
        },
        {
            button: true,
            cell: (row) => {
                return (
                    <Button color="primary" onClick={switchDifferenceModal}>Difference</Button>
                )
            }
        }
    ]

    const handleProfileByDateModalTrigger = (hpId) => {
        if (toggleModal === false) {
            console.log(date.toLocaleDateString())
            apiService.getOverallIInformationByDate(playerWithProfiles.player.uuid, hpId, date.toLocaleDateString().replaceAll(".", "-")).then((req) => {
                if (req.request.status >= 200 && req.request.status < 300) {
                    if (req.request.status === 204) {
                        toast.error("There is no records for with date.")
                    }
                    if (req.data) {
                        setProfileInfo(req.data)
                        setProfileLoading(true)
                        setToggleModal(true)
                        console.log(req.data)
                    }
                } else {
                    console.error("error happened in getPlayerWithProfiles req")
                }
            })
        } else if (toggleModal === true) {
            setProfileInfo(null)
        }
    }

    const handleCurrentProfileModalTrigger = (hpId) => {
        if (toggleModal === false) {
            apiService.getCurrentOverallIInformation(playerWithProfiles.player.uuid, hpId).then((req) => {
                if (req.request.status >= 200 && req.request.status < 300) {
                    if (req.data) {
                        setProfileInfo(req.data)
                        setProfileLoading(true)
                        setToggleModal(true)
                        console.log(req.data)
                    }
                } else {
                    console.error("error happened in getPlayerWithProfiles req")
                }
            })
        } else if (toggleModal === true) {
            setProfileInfo(null)
        }
    }

    const switchToggleModal = () => {
        setToggleModal(!toggleModal)
    }

    const switchDifferenceModal = () => {
        setDifferenceModal(!toggleDifferenceModal)
    }

    useEffect(() => {

        fetchPlayers()
    }, [])

    const fetchPlayers = () => {
        apiService.getPlayerWithProfile(playerId).then((req) => {
            if (req.request.status >= 200 && req.request.status < 300) {
                if (req.data) {
                    setPlayerWithProfiles(req.data)
                    setLoading(true)
                }
            } else {
                console.error("error happened in getPlayerWithProfiles req")
            }
        })
    }


    return (
        <div>
            <ToastContainer
                position="top-right"
                autoClose={2000}
                closeOnClick={true}
                pauseOnHover={true}
            />
            <Navbar/>
            <DatePicker selected={date} onChange={(date) => {
                setDate(date)
                setIsDateSet(true)
            }}
                        locale="ua"
                        dateFormat="dd-MM-yyyy"
            />
            {loading ? (
                <div>
                    <div className="player__information">
                        <p>Id: {playerWithProfiles.player.id}</p>
                        <p>Nickname: {playerWithProfiles.player.nickname}</p>
                        <p>Uuid: {playerWithProfiles.player.uuid}</p>
                        {/*<Button color="primary">Delete player</Button>*/}
                        {/*<Button color="primary">Delete player and profile information</Button>*/}
                        <Button color="primary">Update profiles</Button>
                    </div>
                    <DataTable
                        columns={profileColumns}
                        data={playerWithProfiles.profiles}
                    />
                    <Modal isOpen={toggleModal} toggle={switchToggleModal}>
                        <ModalHeader>Last Saved Profile Information</ModalHeader>
                        <ModalBody>
                            <p>{profileLoading && profileInfo != null ? profileInfo.createdDate : null}</p>
                            <Button id="collectionToggle" color="primary">Collections</Button>
                            <UncontrolledCollapse toggler="#collectionToggle">
                                <Card>
                                    <CardBody>
                                        <div className="collection-container">
                                            {profileLoading && profileInfo.collectionRecord != null ? (
                                                profileInfo.collectionRecord.resources.map(resource => {
                                                    return <p>{resource.name}: {resource.amount}</p>
                                                })
                                            ) : null}
                                        </div>
                                        <br/>
                                    </CardBody>
                                </Card>
                            </UncontrolledCollapse>
                            <Button id="experienceSkillToggle" color="primary">ExperienceSkill</Button>
                            <UncontrolledCollapse toggler="#experienceSkillToggle">
                                <Card>
                                    <CardBody>
                                        {profileLoading && profileInfo.experienceSkillRecord != null ? (
                                            profileInfo.experienceSkillRecord.experienceSkills.map(skill => {
                                                return <p>name: {skill.skillEntity}; exp: {skill.exp};
                                                    level: {skill.level};</p>
                                            })
                                        ) : null}
                                        <br/>
                                    </CardBody>
                                </Card>
                            </UncontrolledCollapse>
                            <Button id="killToggle" color="primary">Kills</Button>
                            <UncontrolledCollapse toggler="#killToggle">
                                <Card>
                                    <CardBody>
                                        <div className="kill-container">
                                            {profileLoading && profileInfo.killRecord != null ? (
                                                profileInfo.killRecord.kills.map(kill => {
                                                    return <p>{kill.killName}: {kill.amount}</p>
                                                })
                                            ) : null}
                                        </div>
                                        <br/>
                                    </CardBody>
                                </Card>
                            </UncontrolledCollapse>
                            <Button id="playerClassToggle" color="primary">PlayerClass</Button>
                            <UncontrolledCollapse toggler="#playerClassToggle">
                                <Card>
                                    <CardBody>
                                        {profileLoading && profileInfo.playerClassRecord != null ? (
                                            profileInfo.playerClassRecord.playerClasses.map(playerClass => {
                                                return <p>class: {playerClass.className}; exp: {playerClass.exp}; level
                                                    - {playerClass.level};</p>
                                            })
                                        ) : null}
                                    </CardBody>
                                </Card>
                            </UncontrolledCollapse>
                            <Button id="balanceToggle" color="primary">Balance</Button>
                            <UncontrolledCollapse toggler="#balanceToggle">
                                <Card>
                                    <CardBody>
                                        {profileLoading && profileInfo.balanceRecord != null ? (
                                            <div>
                                                <p>coins in bank: {profileInfo.balanceRecord.coinsInBank}</p>
                                                <p>coins in purse: {profileInfo.balanceRecord.coinsInPurse}</p>
                                            </div>
                                        ) : null}
                                    </CardBody>
                                </Card>
                            </UncontrolledCollapse>
                        </ModalBody>
                        <ModalFooter>
                            <Button color="danger" onClick={(e) => {
                                switchToggleModal()
                                setProfileInfo(null)
                            }}>
                                Close
                            </Button>
                        </ModalFooter>
                    </Modal>

                    <Modal isOpen={toggleDifferenceModal} toggle={switchDifferenceModal}>
                        <ModalHeader>Header</ModalHeader>
                        <ModalBody>
                            <h1>Body</h1>
                            {/*{dateFrom != null && dateTo != null ? }*/}
                        </ModalBody>
                        <ModalFooter>
                            <Button color="danger" onClick={switchDifferenceModal}>
                                Close
                            </Button>
                        </ModalFooter>
                    </Modal>

                </div>
            ) : null}
        </div>
    )
}

export default PlayerEdit