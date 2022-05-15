import axios from "axios"

const apiService = {}

apiService.getAllPlayers = () => {
    return axios.get("/api/v1/players")
}

apiService.getAllPlayersWithProfiles = () => {
    return axios.get("/api/v1/players/allWithProfiles")
}

apiService.getPlayerWithProfile = (playerId) => {
    return axios.get("/api/v1/players/withProfiles", { params: { playerId } })
}

apiService.getCurrentOverallIInformation = (playerUuid, hpId) => {
    return axios.get("/api/v1/profiles/overallInformation", { params: { playerUuid, hpId} })
}

apiService.getOverallIInformationByDate = (playerUuid, hpId, date) => {
    return axios.get("/api/v1/profiles/overallInformation", { params: { playerUuid, hpId, date} })
}

apiService.deleteAllPlayers = () => {
    return axios.delete("/api/v1/init/players")
}

apiService.deleteAllProfiles = () => {
    return axios.delete("/api/v1/init/profiles")
}

export default apiService