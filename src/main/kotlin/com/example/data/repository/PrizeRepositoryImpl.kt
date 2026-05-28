package com.example.data.repository

import com.example.domain.model.Laureate
import com.example.domain.model.NobelPrize
import com.example.domain.repository.PrizeRepository

class PrizeRepositoryImpl : PrizeRepository {

    private val prizes: List<NobelPrize> = listOf(
        NobelPrize("1", 2023, "physics", listOf(
            Laureate("1", "Pierre Agostini", "1/3", "for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter"),
            Laureate("2", "Ferenc Krausz", "1/3", "for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter"),
            Laureate("3", "Anne L'Huillier", "1/3", "for experimental methods that generate attosecond pulses of light for the study of electron dynamics in matter")
        )),
        NobelPrize("2", 2023, "chemistry", listOf(
            Laureate("4", "Moungi G. Bawendi", "1/3", "for the discovery and synthesis of quantum dots"),
            Laureate("5", "Louis E. Brus", "1/3", "for the discovery and synthesis of quantum dots"),
            Laureate("6", "Alexei I. Ekimov", "1/3", "for the discovery and synthesis of quantum dots")
        )),
        NobelPrize("3", 2023, "medicine", listOf(
            Laureate("7", "Katalin Karikó", "1/2", "for discoveries concerning nucleoside base modifications that enabled the development of effective mRNA vaccines against COVID-19"),
            Laureate("8", "Drew Weissman", "1/2", "for discoveries concerning nucleoside base modifications that enabled the development of effective mRNA vaccines against COVID-19")
        )),
        NobelPrize("4", 2023, "literature", listOf(
            Laureate("9", "Jon Fosse", "1/1", "for his innovative plays and prose which give voice to the unsayable")
        )),
        NobelPrize("5", 2023, "peace", listOf(
            Laureate("10", "Narges Mohammadi", "1/1", "for her fight against the oppression of women in Iran and her efforts to promote human rights and freedom for all")
        )),
        NobelPrize("6", 2023, "economics", listOf(
            Laureate("11", "Claudia Goldin", "1/1", "for having advanced our understanding of women's labour market outcomes")
        )),
        NobelPrize("7", 2022, "physics", listOf(
            Laureate("12", "Alain Aspect", "1/3", "for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science"),
            Laureate("13", "John F. Clauser", "1/3", "for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science"),
            Laureate("14", "Anton Zeilinger", "1/3", "for experiments with entangled photons, establishing the violation of Bell inequalities and pioneering quantum information science")
        )),
        NobelPrize("8", 2022, "chemistry", listOf(
            Laureate("15", "Carolyn R. Bertozzi", "1/3", "for the development of click chemistry and bioorthogonal chemistry"),
            Laureate("16", "Morten Meldal", "1/3", "for the development of click chemistry and bioorthogonal chemistry"),
            Laureate("17", "K. Barry Sharpless", "1/3", "for the development of click chemistry and bioorthogonal chemistry")
        )),
        NobelPrize("9", 2022, "medicine", listOf(
            Laureate("18", "Svante Pääbo", "1/1", "for his discoveries concerning the genomes of extinct hominins and human evolution")
        )),
        NobelPrize("10", 2022, "peace", listOf(
            Laureate("19", "Ales Bialiatski", "1/3", "for their significant efforts to document war crimes, human rights abuses and the abuse of power"),
            Laureate("20", "Memorial", "1/3", "for their significant efforts to document war crimes, human rights abuses and the abuse of power"),
            Laureate("21", "Center for Civil Liberties", "1/3", "for their significant efforts to document war crimes, human rights abuses and the abuse of power")
        )),
        NobelPrize("11", 2021, "physics", listOf(
            Laureate("22", "Syukuro Manabe", "1/4", "for the physical modelling of Earth's climate, quantifying variability and reliably predicting global warming"),
            Laureate("23", "Klaus Hasselmann", "1/4", "for the physical modelling of Earth's climate, quantifying variability and reliably predicting global warming"),
            Laureate("24", "Giorgio Parisi", "1/2", "for the discovery of the interplay of disorder and fluctuations in physical systems from atomic to planetary scales")
        )),
        NobelPrize("12", 2021, "medicine", listOf(
            Laureate("25", "David Julius", "1/2", "for their discoveries of receptors for temperature and touch"),
            Laureate("26", "Ardem Patapoutian", "1/2", "for their discoveries of receptors for temperature and touch")
        ))
    )

    override fun getAllPrizes(): List<NobelPrize> = prizes

    override fun getPrize(year: Int, category: String): NobelPrize? =
        prizes.find { it.year == year && it.category.equals(category, ignoreCase = true) }
}
