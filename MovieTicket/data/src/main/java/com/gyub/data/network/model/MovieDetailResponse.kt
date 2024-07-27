package com.gyub.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/26
 */
@Serializable
data class MovieDetailResponse(
    @SerialName("movieInfoResult") val movieInfoResponse: MovieInfoResponse
)

@Serializable
data class MovieInfoResponse(
    @SerialName("movieInfo") val movieItemInfoResponse: MovieItemInfoResponse,
    @SerialName("source") val source: String
)

@Serializable
data class MovieItemInfoResponse(
    @SerialName("movieCd") val movieCode: String,
    @SerialName("movieNm") val movieName: String,
    @SerialName("movieNmEn") val movieNameEn: String,
    @SerialName("movieNmOg") val movieNameOg: String?,
    @SerialName("showTm") val showTime: String,
    @SerialName("prdtYear") val productionYear: String,
    @SerialName("openDt") val openDate: String,
    @SerialName("prdtStatNm") val productionStatus: String,
    @SerialName("typeNm") val type: String,
    @SerialName("nations") val nationResponses: List<NationResponse>,
    @SerialName("genres") val genreResponses: List<GenreResponse>,
    @SerialName("directors") val directorResponses: List<DirectorResponse>,
    @SerialName("actors") val actorResponses: List<ActorResponse>,
    @SerialName("showTypes") val showTypeResponses: List<ShowTypeResponse>,
    @SerialName("companys") val companies: List<CompanyResponse>,
    @SerialName("audits") val auditResponses: List<AuditResponse>,
    @SerialName("staffs") val staffResponses: List<StaffResponse>
)

@Serializable
data class NationResponse(
    @SerialName("nationNm") val nationName: String
)

@Serializable
data class GenreResponse(
    @SerialName("genreNm") val genreName: String
)

@Serializable
data class DirectorResponse(
    @SerialName("peopleNm") val name: String,
    @SerialName("peopleNmEn") val nameEn: String
)

@Serializable
data class ActorResponse(
    @SerialName("peopleNm") val name: String,
    @SerialName("peopleNmEn") val nameEn: String,
    @SerialName("cast") val cast: String,
    @SerialName("castEn") val castEn: String?
)

@Serializable
data class ShowTypeResponse(
    @SerialName("showTypeGroupNm") val showTypeGroupName: String,
    @SerialName("showTypeNm") val showTypeName: String
)

@Serializable
data class CompanyResponse(
    @SerialName("companyCd") val companyCode: String,
    @SerialName("companyNm") val companyName: String,
    @SerialName("companyNmEn") val companyNameEn: String,
    @SerialName("companyPartNm") val companyPartName: String
)

@Serializable
data class AuditResponse(
    @SerialName("auditNo") val auditNumber: String,
    @SerialName("watchGradeNm") val watchGradeName: String
)

@Serializable
data class StaffResponse(
    @SerialName("peopleNm") val name: String,
    @SerialName("peopleNmEn") val nameEn: String,
    @SerialName("staffRoleNm") val role: String
)