/*
 *
 * Copyright 2016 EUROPEAN COMMISSION
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/community/eupl/og_page/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 *
 */

package eu.europa.ec.grow.espd.xml.common.importing;

import com.google.common.base.Optional;
import eu.europa.ec.grow.espd.domain.*;
import eu.europa.ec.grow.espd.domain.OtherCriterion;
import eu.europa.ec.grow.espd.domain.enums.criteria.*;
import eu.europa.ec.grow.espd.domain.intf.MultipleAmountHolder;
import eu.europa.ec.grow.espd.domain.intf.MultipleDescriptionHolder;
import eu.europa.ec.grow.espd.domain.intf.MultipleYearHolder;
import eu.europa.ec.grow.espd.domain.intf.UnboundedRequirementGroup;
import eu.europa.ec.grow.espd.domain.ubl.CcvCriterion;
import eu.europa.ec.grow.espd.domain.ubl.CcvCriterionRequirement;
import eu.europa.ec.grow.espd.domain.ubl.CcvRequirementGroup;
import eu.europa.ec.grow.espd.domain.ubl.CcvResponseType;
import eu.europa.ec.grow.espd.util.Amount;
import isa.names.specification.ubl.schema.xsd.ccv_commonaggregatecomponents_1.CriterionType;
import isa.names.specification.ubl.schema.xsd.ccv_commonaggregatecomponents_1.RequirementGroupType;
import isa.names.specification.ubl.schema.xsd.ccv_commonaggregatecomponents_1.RequirementType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Class that reads data coming from UBL {@link CriterionType} and creates the appropriate ESPD criterion objects
 * containing all the data read from the XML.
 * <p>
 * The algorithm traverses all the {@link RequirementGroupType} recursively (including subgroups) found in the
 * {@link CriterionType} and looks up each requirement by id in the {@link CriteriaDefinitions} lookup tables.
 * The criterion requirements are then set dynamically via reflection by using its 'espdCriterionFields' loaded from
 * the criteria JSON definition files.
 * </p>
 * <p>
 * Requirement groups are treated differently when they are unbounded (which means they can ashave  many references as
 * needed) because the requirement values are dynamically stored in a {@link java.util.Map} structure called
 * {@link DynamicRequirementGroup}.
 * </p>
 * <p>
 * For the unbounded requirements there are mappings defined in the configuration JSON files ('requirementGroupMappings'
 * and 'requirementMappings') for supporting versions prior to 2016.12 when there were 3 or 5 requirement groups with
 * a similar structure but sequential ESPD criterion field mappings. Since now there is only one unbounded requirement
 * group for a given criterion, we need to point the old requirement group ids and requirement ids to the primary ones
 * used from version 2016.12.
 * </p>
 * <p>
 * Created by ratoico on 1/7/16 at 11:16 AM.
 */
@Slf4j
class EspdResponseCriterionFactory {

	private static final CcvCriterionRequirement OLD_SELECTION_PLEASE_SPECIFY_REQUIREMENT = buildOldSelectionPleaseSpecifyRequirement();

	/**
	 * Create a ESPD {@link EspdCriterion} instance containing the appropriate information provided as UBL criteria.
	 *
	 * @param <T> The type of {@link EspdCriterion}
	 *
	 * @return A freshly built {@link EspdCriterion} containing the data coming from the XML
	 *
	 * @throws IllegalArgumentException If the criterion type is not recognized
	 */
	@SuppressWarnings("unchecked")
	<T extends EspdCriterion> T buildEspdCriterion(CcvCriterion ccvCriterion, CriterionType ublCriterion) {

		if (ExclusionCriterionTypeCode.CRIMINAL_CONVICTIONS.getEspdType()
		                                                   .equals(ccvCriterion.getCriterionType().getEspdType())) {
			return (T) buildCriminalConvictionsCriterion(ublCriterion);
		} else if (ExclusionCriterionTypeCode.PAYMENT_OF_TAXES.getEspdType()
		                                                      .equals(ccvCriterion.getCriterionType().getEspdType()) ||
				ExclusionCriterionTypeCode.PAYMENT_OF_SOCIAL_SECURITY.getEspdType()
				                                                     .equals(ccvCriterion.getCriterionType()
				                                                                         .getEspdType())) {
			return (T) buildTaxesCriterion(ublCriterion);
		} else if (ExclusionCriterionTypeCode.ENVIRONMENTAL_LAW.getEspdType()
		                                                       .equals(ccvCriterion.getCriterionType().getEspdType()) ||
				ExclusionCriterionTypeCode.SOCIAL_LAW.getEspdType()
				                                     .equals(ccvCriterion.getCriterionType().getEspdType()) ||
				ExclusionCriterionTypeCode.LABOUR_LAW.getEspdType()
				                                     .equals(ccvCriterion.getCriterionType().getEspdType())) {
			return (T) buildLawCriterion(ublCriterion);
		} else if (ExclusionCriterionTypeCode.BANKRUPTCY_INSOLVENCY.getEspdType()
		                                                           .equals(ccvCriterion.getCriterionType()
		                                                                               .getEspdType())) {
			return (T) buildBankruptcyCriterion(ublCriterion);
		} else if (ExclusionCriterionTypeCode.MISCONDUCT.getEspdType()
		                                                .equals(ccvCriterion.getCriterionType().getEspdType()) ||
				ExclusionCriterionTypeCode.DISTORTING_MARKET.getEspdType()
				                                            .equals(ccvCriterion.getCriterionType().getEspdType())) {
			return (T) buildMisconductCriterion(ublCriterion);
		} else if (ExclusionCriterionTypeCode.CONFLICT_OF_INTEREST.getEspdType()
		                                                          .equals(ccvCriterion.getCriterionType()
		                                                                              .getEspdType())) {
			return (T) buildConflictOfInterestCriterion(ublCriterion);
		} else if (ExclusionCriterionTypeCode.OTHER.getEspdType()
		                                           .equals(ccvCriterion.getCriterionType().getEspdType())) {
			return (T) buildPurelyNationalGrounds(ublCriterion);
		} else if (SelectionCriterionTypeCode.ALL_CRITERIA_SATISFIED.getEspdType()
		                                                            .equals(ccvCriterion.getCriterionType()
		                                                                                .getEspdType())) {
			return (T) buildSatisfiesAllCriterion(ublCriterion);
		} else if (SelectionCriterionTypeCode.SUITABILITY.getEspdType()
		                                                 .equals(ccvCriterion.getCriterionType().getEspdType())) {
			return (T) buildSuitabilityCriterion(ublCriterion);
		} else if (SelectionCriterionTypeCode.ECONOMIC_FINANCIAL_STANDING.getEspdType()
		                                                                 .equals(ccvCriterion.getCriterionType()
		                                                                                     .getEspdType())) {
			return (T) buildEconomicFinancialStandingCriterion(ublCriterion);
		} else if (SelectionCriterionTypeCode.TECHNICAL_PROFESSIONAL_ABILITY.getEspdType()
		                                                                    .equals(ccvCriterion.getCriterionType()
		                                                                                        .getEspdType())) {
			return (T) buildTechnicalProfessionalCriterion(ublCriterion);
		} else if (SelectionCriterionTypeCode.QUALITY_ASSURANCE.getEspdType()
		                                                       .equals(ccvCriterion.getCriterionType().getEspdType())) {
			return (T) buildQualityAssuranceCriterion(ublCriterion);
		} else if (OtherCriterionTypeCode.DATA_ON_ECONOMIC_OPERATOR.getEspdType()
		                                                           .equals(ccvCriterion.getCriterionType()
		                                                                               .getEspdType()) ||
				OtherCriterionTypeCode.REDUCTION_OF_CANDIDATES.getEspdType()
				                                              .equals(ccvCriterion.getCriterionType().getEspdType())) {
			return (T) buildAwardCriterion(ublCriterion);
		}
		throw new IllegalArgumentException(
				String.format("Could not build criterion '%s' with id '%s' having type code '%s'.",
						ccvCriterion.getName(), ccvCriterion.getUuid(), ccvCriterion.getCriterionType()));
	}

	private CriminalConvictionsCriterion buildCriminalConvictionsCriterion(CriterionType criterionType) {
		if (criterionType == null) {
			return CriminalConvictionsCriterion.buildWithExists(false);
		}

		CriminalConvictionsCriterion criterion = CriminalConvictionsCriterion.buildWithExists(true);

		setCriterionValues(criterionType, criterion);

		//		Boolean yourAnswer = readExclusionCriterionAnswer(criterionType);
		//		criterion.setAnswer(yourAnswer);
		//
		//		Date dateOfConviction = readRequirementValue(ExclusionCriterionRequirement.DATE_OF_CONVICTION, criterionType);
		//		criterion.setDateOfConviction(dateOfConviction);
		//		String reason = readRequirementValue(ExclusionCriterionRequirement.REASON, criterionType);
		//		criterion.setReason(reason);
		//		String whoConvicted = readRequirementValue(ExclusionCriterionRequirement.WHO_CONVICTED, criterionType);
		//		criterion.setConvicted(whoConvicted);
		//		String periodLength = readRequirementValue(ExclusionCriterionRequirement.LENGTH_PERIOD_EXCLUSION,
		//				criterionType);
		//		criterion.setPeriodLength(periodLength);
		//
		//		criterion.setSelfCleaning(buildSelfCleaningMeasures(criterionType));
		//
		//		criterion.setAvailableElectronically(buildExclusionAvailableElectronically(criterionType));
		return criterion;
	}

	private TaxesCriterion buildTaxesCriterion(CriterionType criterionType) {
		if (criterionType == null) {
			return TaxesCriterion.buildWithExists(false);
		}

		TaxesCriterion criterion = TaxesCriterion.buildWithExists(true);
		setCriterionValues(criterionType, criterion);

		//		Boolean yourAnswer = readExclusionCriterionAnswer(criterionType);
		//		criterion.setAnswer(yourAnswer);
		//
		//		Country country = readRequirementValue(ExclusionCriterionRequirement.COUNTRY_MS, criterionType);
		//		criterion.setCountry(country);
		//		Amount amount = readRequirementValue(ExclusionCriterionRequirement.AMOUNT, criterionType);
		//		if (amount != null) {
		//			criterion.setAmount(amount.getAmount());
		//			criterion.setCurrency(amount.getCurrency());
		//		}
		//
		//		Boolean breach = readBooleanRequirement(ExclusionCriterionRequirement.BREACH_OF_OBLIGATIONS_OTHER_THAN,
		//				criterionType);
		//		criterion.setBreachEstablishedOtherThanJudicialDecision(breach);
		//		String meansDescription = readRequirementValue(ExclusionCriterionRequirement.DESCRIBE_MEANS, criterionType);
		//		criterion.setMeansDescription(meansDescription);
		//
		//		Boolean finalAndBinding = readBooleanRequirement(ExclusionCriterionRequirement.DECISION_FINAL_AND_BINDING,
		//				criterionType);
		//		criterion.setDecisionFinalAndBinding(finalAndBinding);
		//		Date dateOfConviction = readRequirementValue(ExclusionCriterionRequirement.DATE_OF_CONVICTION, criterionType);
		//		criterion.setDateOfConviction(dateOfConviction);
		//		String periodLength = readRequirementValue(ExclusionCriterionRequirement.LENGTH_PERIOD_EXCLUSION,
		//				criterionType);
		//		criterion.setPeriodLength(periodLength);
		//
		//		Boolean fulfilledObligation = readBooleanRequirement(ExclusionCriterionRequirement.EO_FULFILLED_OBLIGATION,
		//				criterionType);
		//		criterion.setEoFulfilledObligations(fulfilledObligation);
		//		String obligationDescription = readRequirementValue(ExclusionCriterionRequirement.DESCRIBE_OBLIGATIONS,
		//				criterionType);
		//		criterion.setObligationsDescription(obligationDescription);
		//
		//		criterion.setAvailableElectronically(buildExclusionAvailableElectronically(criterionType));

		return criterion;
	}

	private LawCriterion buildLawCriterion(CriterionType criterionType) {
		if (criterionType == null) {
			return LawCriterion.buildWithExists(false);
		}

		LawCriterion criterion = LawCriterion.buildWithExists(true);
		setCriterionValues(criterionType, criterion);
		//		Boolean yourAnswer = readExclusionCriterionAnswer(criterionType);
		//		criterion.setAnswer(yourAnswer);
		//
		//		String description = readRequirementValue(ExclusionCriterionRequirement.PLEASE_DESCRIBE, criterionType);
		//		criterion.setDescription(description);
		//
		//		criterion.setSelfCleaning(buildSelfCleaningMeasures(criterionType));

		return criterion;
	}

	private BankruptcyCriterion buildBankruptcyCriterion(CriterionType criterionType) {
		if (criterionType == null) {
			return BankruptcyCriterion.buildWithExists(false);
		}

		BankruptcyCriterion criterion = BankruptcyCriterion.buildWithExists(true);
		setCriterionValues(criterionType, criterion);

		//		Boolean yourAnswer = readExclusionCriterionAnswer(criterionType);
		//		criterion.setAnswer(yourAnswer);
		//
		//		String description = readRequirementValue(ExclusionCriterionRequirement.PLEASE_DESCRIBE, criterionType);
		//		criterion.setDescription(description);
		//		String reasonContract = readRequirementValue(ExclusionCriterionRequirement.REASONS_NEVERTHELESS_CONTRACT,
		//				criterionType);
		//		criterion.setReason(reasonContract);
		//
		//		criterion.setAvailableElectronically(buildExclusionAvailableElectronically(criterionType));

		return criterion;
	}

	private MisconductDistortionCriterion buildMisconductCriterion(CriterionType criterionType) {
		if (criterionType == null) {
			return MisconductDistortionCriterion.buildWithExists(false);
		}

		MisconductDistortionCriterion criterion = MisconductDistortionCriterion.buildWithExists(true);
		setCriterionValues(criterionType, criterion);

		//		Boolean yourAnswer = readExclusionCriterionAnswer(criterionType);
		//		criterion.setAnswer(yourAnswer);
		//
		//		String description = readRequirementValue(ExclusionCriterionRequirement.PLEASE_DESCRIBE, criterionType);
		//		criterion.setDescription(description);
		//
		//		criterion.setSelfCleaning(buildSelfCleaningMeasures(criterionType));
		//		criterion.setAvailableElectronically(buildExclusionAvailableElectronically(criterionType));

		return criterion;
	}

	private ConflictInterestCriterion buildConflictOfInterestCriterion(CriterionType criterionType) {
		if (criterionType == null) {
			return ConflictInterestCriterion.buildWithExists(false);
		}

		ConflictInterestCriterion criterion = ConflictInterestCriterion.buildWithExists(true);
		setCriterionValues(criterionType, criterion);

		//		Boolean yourAnswer = readExclusionCriterionAnswer(criterionType);
		//		criterion.setAnswer(yourAnswer);
		//
		//		String description = readRequirementValue(ExclusionCriterionRequirement.PLEASE_DESCRIBE, criterionType);
		//		criterion.setDescription(description);
		//
		//		criterion.setSelfCleaning(buildSelfCleaningMeasures(criterionType));
		//		criterion.setAvailableElectronically(buildExclusionAvailableElectronically(criterionType));

		return criterion;
	}

	private PurelyNationalGrounds buildPurelyNationalGrounds(CriterionType criterionType) {
		if (criterionType == null) {
			return PurelyNationalGrounds.buildWithExists(false);
		}
		PurelyNationalGrounds criterion = PurelyNationalGrounds.buildWithExists(true);
		setCriterionValues(criterionType, criterion);

		//		Boolean yourAnswer = readExclusionCriterionAnswer(criterionType);
		//		criterion.setAnswer(yourAnswer);
		//
		//		String description = readRequirementValue(ExclusionCriterionRequirement.PLEASE_DESCRIBE, criterionType);
		//		criterion.setDescription(description);
		//
		//		criterion.setSelfCleaning(buildSelfCleaningMeasures(criterionType));
		//		criterion.setAvailableElectronically(buildExclusionAvailableElectronically(criterionType));

		return criterion;
	}

	private SatisfiesAllCriterion buildSatisfiesAllCriterion(CriterionType criterionType) {
		if (criterionType == null) {
			return SatisfiesAllCriterion.buildWithExists(false);
		}

		SatisfiesAllCriterion criterion = SatisfiesAllCriterion.buildWithExists(true);
		setCriterionValues(criterionType, criterion);

		//		Boolean yourAnswer = readSelectionCriterionAnswer(criterionType);
		//		criterion.setAnswer(yourAnswer);

		return criterion;
	}

	private SuitabilityCriterion buildSuitabilityCriterion(CriterionType criterionType) {
		if (criterionType == null) {
			return SuitabilityCriterion.buildWithExists(false);
		}

		SuitabilityCriterion criterion = SuitabilityCriterion.buildWithExists(true);
		setCriterionValues(criterionType, criterion);

		//		Boolean yourAnswer = readSelectionCriterionAnswer(criterionType);
		//		criterion.setAnswer(yourAnswer);
		//
		//		String description = readRequirementValue(SelectionCriterionRequirement.PLEASE_DESCRIBE, criterionType);
		//		criterion.setDescription(description);
		//
		//		criterion.setAvailableElectronically(buildSelectionAvailableElectronically(criterionType));

		return criterion;
	}

	private EconomicFinancialStandingCriterion buildEconomicFinancialStandingCriterion(CriterionType criterionType) {
		if (criterionType == null) {
			return EconomicFinancialStandingCriterion.buildWithExists(false);
		}

		EconomicFinancialStandingCriterion criterion = EconomicFinancialStandingCriterion.buildWithExists(true);
		setCriterionValues(criterionType, criterion);

		//		Boolean yourAnswer = readSelectionCriterionAnswer(criterionType);
		//		criterion.setAnswer(yourAnswer);

		//		addMultipleYears(criterionType, criterion);
		addMultipleAmounts(criterionType, criterion);
		addMultipleDescriptions(criterionType, criterion);
		//		addMultipleRatios(criterionType, criterion);

		//		String description = readRequirementValue(SelectionCriterionRequirement.PLEASE_DESCRIBE, criterionType);
		//		criterion.setDescription(description);
		//		// year 1 is overloaded because of criterion 10. Set up of economic operator
		//		Integer year1 = readRequirementValue(SelectionCriterionRequirement.SPECIFY_YEAR, criterionType);
		//		if (year1 != null) {
		//			criterion.setYear1(year1);
		//		}
		//
		//		Integer numberOfYears = readRequirementValue(SelectionCriterionRequirement.NUMBER_OF_YEARS, criterionType);
		//		if (numberOfYears != null) {
		//			criterion.setNumberOfYears(numberOfYears);
		//		}
		//		Amount turnover = readRequirementValue(SelectionCriterionRequirement.AVERAGE_TURNOVER, criterionType);
		//		if (turnover != null) {
		//			criterion.setAverageTurnover(turnover.getAmount());
		//			criterion.setAverageTurnoverCurrency(turnover.getCurrency());
		//		}

		//		criterion.setAvailableElectronically(buildSelectionAvailableElectronically(criterionType));

		return criterion;
	}

	private void addMultipleYears(CriterionType criterionType, MultipleYearHolder criterion) {
		Integer year1 = readRequirementValue(SelectionCriterionRequirement.YEAR_1, criterionType);
		if (year1 != null) {
			criterion.setYear1(year1);
		}
		Integer year2 = readRequirementValue(SelectionCriterionRequirement.YEAR_2, criterionType);
		if (year2 != null) {
			criterion.setYear2(year2);
		}
		Integer year3 = readRequirementValue(SelectionCriterionRequirement.YEAR_3, criterionType);
		if (year3 != null) {
			criterion.setYear3(year3);
		}
		Integer year4 = readRequirementValue(SelectionCriterionRequirement.YEAR_4, criterionType);
		if (year4 != null) {
			criterion.setYear4(year4);
		}
		Integer year5 = readRequirementValue(SelectionCriterionRequirement.YEAR_5, criterionType);
		if (year5 != null) {
			criterion.setYear5(year5);
		}
	}

	private void addMultipleRatios(CriterionType criterionType, EconomicFinancialStandingCriterion criterion) {
		BigDecimal ratio1 = readRequirementValue(SelectionCriterionRequirement.RATIO_1, criterionType);
		criterion.setRatio1(ratio1);
		BigDecimal ratio2 = readRequirementValue(SelectionCriterionRequirement.RATIO_2, criterionType);
		criterion.setRatio2(ratio2);
		BigDecimal ratio3 = readRequirementValue(SelectionCriterionRequirement.RATIO_3, criterionType);
		criterion.setRatio3(ratio3);
		BigDecimal ratio4 = readRequirementValue(SelectionCriterionRequirement.RATIO_4, criterionType);
		criterion.setRatio4(ratio4);
		BigDecimal ratio5 = readRequirementValue(SelectionCriterionRequirement.RATIO_5, criterionType);
		criterion.setRatio5(ratio5);
	}

	private TechnicalProfessionalCriterion buildTechnicalProfessionalCriterion(CriterionType criterionType) {
		if (criterionType == null) {
			return TechnicalProfessionalCriterion.buildWithExists(false);
		}

		TechnicalProfessionalCriterion criterion = TechnicalProfessionalCriterion.buildWithExists(true);
		setCriterionValues(criterionType, criterion);

		// because of allow checks requirement we are overloading the 'answer' field
		// this is a workaround in order to not add one additional Boolean field 'allowChecks' that is used by only technical professional criterion
		//		Boolean allowChecks = readRequirementValue(SelectionCriterionRequirement.ALLOW_CHECKS, criterionType);
		//		if (allowChecks != null) {
		//			criterion.setAnswer(allowChecks);
		//		}
		//		Boolean yourAnswer = readRequirementValue(SelectionCriterionRequirement.YOUR_ANSWER, criterionType);
		//		if (yourAnswer != null) {
		//			criterion.setAnswer(yourAnswer);
		//		}
		//
		//		addMultipleYears(criterionType, criterion);
		//		addMultipleNumbers(criterionType, criterion);
		//
		//
		//		addDescriptionForTechnicalProfessional(criterionType, criterion);
		//
		//		String specify = readRequirementValue(SelectionCriterionRequirement.PLEASE_SPECIFY, criterionType);
		//		if (isBlank(specify)) {
		//			// backwards compatibility check, the id of this requirement was changed in version 2016.06.01
		//			// due to conflict with an existing selection criterion id
		//			specify = readRequirementValue(OLD_SELECTION_PLEASE_SPECIFY_REQUIREMENT, criterionType);
		//		}
		//		criterion.setSpecify(specify);
		//
		//		BigDecimal percentage = readRequirementValue(SelectionCriterionRequirement.PERCENTAGE, criterionType);
		//		criterion.setPercentage(percentage);
		//
		//		criterion.setAvailableElectronically(buildSelectionAvailableElectronically(criterionType));

		return criterion;
	}

	private static CcvCriterionRequirement buildOldSelectionPleaseSpecifyRequirement() {
		return new CcvCriterionRequirement() {
			@Override
			public String getId() {
				return "3aaca389-4a7b-406b-a4b9-080845d127e7";
			}

			@Override
			public String getDescription() {
				return SelectionCriterionRequirement.PLEASE_SPECIFY.getDescription();
			}

			@Override
			public CcvResponseType getResponseType() {
				return SelectionCriterionRequirement.PLEASE_SPECIFY.getResponseType();
			}

			@Override
			public List<String> getEspdCriterionFields() {
				return SelectionCriterionRequirement.PLEASE_SPECIFY.getEspdCriterionFields();
			}
		};
	}

	private QualityAssuranceCriterion buildQualityAssuranceCriterion(CriterionType criterionType) {
		if (criterionType == null) {
			return QualityAssuranceCriterion.buildWithExists(false);
		}

		QualityAssuranceCriterion criterion = QualityAssuranceCriterion.buildWithExists(true);
		setCriterionValues(criterionType, criterion);

		//		Boolean yourAnswer = readRequirementValue(SelectionCriterionRequirement.YOUR_ANSWER, criterionType);
		//		if (yourAnswer != null) {
		//			criterion.setAnswer(yourAnswer);
		//		}
		//
		//		String explainQa = readRequirementValue(SelectionCriterionRequirement.EXPLAIN_CERTIFICATES_QA, criterionType);
		//		if (isNotBlank(explainQa)) {
		//			criterion.setDescription(explainQa);
		//		}
		//		String explainEnvironmental = readRequirementValue(
		//				SelectionCriterionRequirement.EXPLAIN_CERTIFICATES_ENVIRONMENTAL, criterionType);
		//		if (isNotBlank(explainEnvironmental)) {
		//			criterion.setDescription(explainEnvironmental);
		//		}
		//
		//		criterion.setAvailableElectronically(buildSelectionAvailableElectronically(criterionType));

		return criterion;
	}

	private void addDescriptionForTechnicalProfessional(CriterionType criterionType,
			TechnicalProfessionalCriterion criterion) {
		// description is overloaded so we cannot have all these fields set at the same time
		// the design is not very good
		String explainSupplyQc = readRequirementValue(SelectionCriterionRequirement.EXPLAIN_SUPPLY_CONTRACTS_QC,
				criterionType);
		if (isNotBlank(explainSupplyQc)) {
			criterion.setDescription(explainSupplyQc);
		}
		String explainCertificatesQa = readRequirementValue(SelectionCriterionRequirement.EXPLAIN_CERTIFICATES_QA,
				criterionType);
		if (isNotBlank(explainCertificatesQa)) {
			criterion.setDescription(explainCertificatesQa);
		}
		String explainCertificatesEnvironmental = readRequirementValue(
				SelectionCriterionRequirement.EXPLAIN_CERTIFICATES_ENVIRONMENTAL, criterionType);
		if (isNotBlank(explainCertificatesEnvironmental)) {
			criterion.setDescription(explainCertificatesEnvironmental);
		}
		String pleaseDescribe = readRequirementValue(SelectionCriterionRequirement.PLEASE_DESCRIBE, criterionType);
		if (isNotBlank(pleaseDescribe)) {
			criterion.setDescription(pleaseDescribe);
		}
	}

	private void addMultipleNumbers(CriterionType criterionType, TechnicalProfessionalCriterion criterion) {
		Integer number1 = readRequirementValue(SelectionCriterionRequirement.NUMBER_1, criterionType);
		if (number1 != null) {
			criterion.setNumber1(number1);
		}
		Integer number2 = readRequirementValue(SelectionCriterionRequirement.NUMBER_2, criterionType);
		if (number2 != null) {
			criterion.setNumber2(number2);
		}
		Integer number3 = readRequirementValue(SelectionCriterionRequirement.NUMBER_3, criterionType);
		if (number3 != null) {
			criterion.setNumber3(number3);
		}
	}

	private void addMultipleDescriptions(CriterionType criterionType, MultipleDescriptionHolder criterion) {
		String description1 = readRequirementValue(SelectionCriterionRequirement.DESCRIPTION_1, criterionType);
		criterion.setDescription1(description1);
		String description2 = readRequirementValue(SelectionCriterionRequirement.DESCRIPTION_2, criterionType);
		criterion.setDescription2(description2);
		String description3 = readRequirementValue(SelectionCriterionRequirement.DESCRIPTION_3, criterionType);
		criterion.setDescription3(description3);
		String description4 = readRequirementValue(SelectionCriterionRequirement.DESCRIPTION_4, criterionType);
		criterion.setDescription4(description4);
		String description5 = readRequirementValue(SelectionCriterionRequirement.DESCRIPTION_5, criterionType);
		criterion.setDescription5(description5);
	}

	private void setCriterionValues(CriterionType criterionType, EspdCriterion espdCriterion) {
		setCriterionValuesForRequirementGroups(espdCriterion, criterionType.getRequirementGroup());
	}

	private void setCriterionValuesForRequirementGroups(EspdCriterion espdCriterion,
			List<RequirementGroupType> groups) {
		if (isEmpty(groups)) {
			return;
		}
		for (RequirementGroupType groupType : groups) {
			setCriterionValuesForRequirementGroup(espdCriterion, groupType);
		}
	}

	private void setCriterionValuesForRequirementGroup(EspdCriterion espdCriterion, RequirementGroupType groupType) {
		Optional<CcvRequirementGroup> ccvGroup = CriteriaDefinitions
				.findRequirementGroupById(groupType.getID().getValue());
		DynamicRequirementGroup dynamicGroup = null;
		if (ccvGroup.isPresent() && ccvGroup.get().isUnbounded()) {
			dynamicGroup = new DynamicRequirementGroup();
			((UnboundedRequirementGroup) espdCriterion).getUnboundedGroups().add(dynamicGroup);
		}

		setCriterionValuesForRequirementGroups(espdCriterion, groupType.getRequirementGroup());
		setCriterionValueForRequirements(espdCriterion, groupType.getRequirement(), ccvGroup, dynamicGroup);
	}

	private void setCriterionValueForRequirements(EspdCriterion espdCriterion, List<RequirementType> requirementTypes,
			Optional<CcvRequirementGroup> ccvGroup, DynamicRequirementGroup dynamicGroup) {
		if (isEmpty(requirementTypes)) {
			return;
		}
		for (RequirementType requirementType : requirementTypes) {
			setCriterionValueForRequirement(espdCriterion, requirementType, ccvGroup, dynamicGroup);
		}
	}

	private void setCriterionValueForRequirement(EspdCriterion espdCriterion, RequirementType requirementType,
			Optional<CcvRequirementGroup> ccvGroup, DynamicRequirementGroup dynamicGroup) {
		Optional<CcvCriterionRequirement> requirementById = CriteriaDefinitions
				.findRequirementById(requirementType.getID().getValue());

		if (requirementById.isPresent() && isNotEmpty(requirementType.getResponse())) {
			Object requirementValue = requirementById.get().getResponseType()
			                                         .parseValue(requirementType.getResponse().get(0));
			if (ccvGroup.isPresent() && ccvGroup.get().isUnbounded()) {
				addRequirementValueToUnboundedGroup(requirementById.get(), dynamicGroup, requirementValue);
			} else {
				addNormalRequirementValueToEspdCriterion(requirementById.get(), espdCriterion, requirementValue);
			}
		} else {
			log.warn("Requirement with id '{}' could not be found or does not have a response.",
					requirementType.getID().getValue());
		}

	}

	private void addRequirementValueToUnboundedGroup(CcvCriterionRequirement ccvRequirement,
			DynamicRequirementGroup dynamicGroup, Object value) {
		if (value instanceof Amount) {
			// values which represent amounts are special and need to be stored in two fields
			dynamicGroup.put(ccvRequirement.getEspdCriterionFields().get(0), ((Amount) value).getAmount());
			dynamicGroup.put(ccvRequirement.getEspdCriterionFields().get(1), ((Amount) value).getCurrency());
		} else {
			dynamicGroup.put(ccvRequirement.getEspdCriterionFields().get(0), value);
		}
	}

	private void addNormalRequirementValueToEspdCriterion(CcvCriterionRequirement ccvCriterionRequirement,
			EspdCriterion espdCriterion, Object value) {
		String espdFieldName = ccvCriterionRequirement.getEspdCriterionFields().get(0);
		if (isBlank(espdFieldName)) {
			return;
		}

		// values which represent amounts are special and need to be stored in two fields
		try {
			if (value instanceof Amount) {
				PropertyUtils.setProperty(espdCriterion, espdFieldName, ((Amount) value).getAmount());
				PropertyUtils.setProperty(espdCriterion, ccvCriterionRequirement.getEspdCriterionFields().get(1),
						((Amount) value).getCurrency());
			} else {
				PropertyUtils.setProperty(espdCriterion, espdFieldName, value);
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			log.error("Could not set value '{}' on field '{}' of requirement '{}' with id '{}'.", value, espdFieldName,
					ccvCriterionRequirement.getDescription(), ccvCriterionRequirement.getId());
		}
	}

	private void addMultipleAmounts(CriterionType criterionType, MultipleAmountHolder criterion) {
		Amount amount1 = readRequirementValue(SelectionCriterionRequirement.AMOUNT_1, criterionType);
		if (amount1 != null) {
			criterion.setAmount1(amount1.getAmount());
			criterion.setCurrency1(amount1.getCurrency());
		}
		Amount amount2 = readRequirementValue(SelectionCriterionRequirement.AMOUNT_2, criterionType);
		if (amount2 != null) {
			criterion.setAmount2(amount2.getAmount());
			criterion.setCurrency2(amount2.getCurrency());
		}
		Amount amount3 = readRequirementValue(SelectionCriterionRequirement.AMOUNT_3, criterionType);
		if (amount3 != null) {
			criterion.setAmount3(amount3.getAmount());
			criterion.setCurrency3(amount3.getCurrency());
		}
		Amount amount4 = readRequirementValue(SelectionCriterionRequirement.AMOUNT_4, criterionType);
		if (amount4 != null) {
			criterion.setAmount4(amount4.getAmount());
			criterion.setCurrency4(amount4.getCurrency());
		}
		Amount amount5 = readRequirementValue(SelectionCriterionRequirement.AMOUNT_5, criterionType);
		if (amount5 != null) {
			criterion.setAmount5(amount5.getAmount());
			criterion.setCurrency5(amount5.getCurrency());
		}
	}

	private Boolean readExclusionCriterionAnswer(CriterionType criterionType) {
		return readCriterionAnswer(criterionType, ExclusionCriterionRequirement.YOUR_ANSWER);
	}

	private Boolean readSelectionCriterionAnswer(CriterionType criterionType) {
		return readCriterionAnswer(criterionType, SelectionCriterionRequirement.YOUR_ANSWER);
	}

	private Boolean readAwardCriterionAnswer(CriterionType criterionType) {
		return readCriterionAnswer(criterionType, OtherCriterionRequirement.INDICATOR);
	}

	private Boolean readCriterionAnswer(CriterionType criterionType, CcvCriterionRequirement answerReq) {
		return readRequirementValue(answerReq, criterionType);
	}

	private SelfCleaning buildSelfCleaningMeasures(CriterionType criterionType) {
		Boolean selfCleaningAnswer = readBooleanRequirement(ExclusionCriterionRequirement.MEASURES_SELF_CLEANING,
				criterionType);
		SelfCleaning selfCleaning = new SelfCleaning();
		selfCleaning.setAnswer(selfCleaningAnswer);
		String description = readRequirementValue(ExclusionCriterionRequirement.PLEASE_DESCRIBE_SELF_CLEANING,
				criterionType);
		selfCleaning.setDescription(description);
		return selfCleaning;
	}

	private AvailableElectronically buildExclusionAvailableElectronically(CriterionType criterionType) {
		return buildAvailableElectronically(criterionType, ExclusionCriterionRequirement.INFO_AVAILABLE_ELECTRONICALLY,
				ExclusionCriterionRequirement.URL, ExclusionCriterionRequirement.URL_CODE,
				ExclusionCriterionRequirement.ISSUER);
	}

	private AvailableElectronically buildSelectionAvailableElectronically(CriterionType criterionType) {
		return buildAvailableElectronically(criterionType, SelectionCriterionRequirement.INFO_AVAILABLE_ELECTRONICALLY,
				SelectionCriterionRequirement.URL, SelectionCriterionRequirement.URL_CODE,
				SelectionCriterionRequirement.ISSUER);
	}

	private AvailableElectronically buildAwardAvailableElectronically(CriterionType criterionType) {
		return buildAvailableElectronically(criterionType, OtherCriterionRequirement.INFO_AVAILABLE_ELECTRONICALLY,
				OtherCriterionRequirement.URL, OtherCriterionRequirement.URL_CODE, OtherCriterionRequirement.ISSUER);
	}

	private AvailableElectronically buildAvailableElectronically(CriterionType criterionType,
			CcvCriterionRequirement answerReq, CcvCriterionRequirement urlReq, CcvCriterionRequirement urlCodeReq,
			CcvCriterionRequirement issuerReq) {
		AvailableElectronically electronically = new AvailableElectronically();
		Boolean electronicallyAnswer = readBooleanRequirement(answerReq, criterionType);
		electronically.setAnswer(electronicallyAnswer);
		String url = readRequirementValue(urlReq, criterionType);
		electronically.setUrl(url);
		String code = readRequirementValue(urlCodeReq, criterionType);
		electronically.setCode(code);
		String issuer = readRequirementValue(issuerReq, criterionType);
		electronically.setIssuer(issuer);
		return electronically;
	}

	private OtherCriterion buildAwardCriterion(CriterionType criterionType) {
		OtherCriterion criterion = OtherCriterion.build();

		if (criterionType == null) {
			return criterion;
		}

		setCriterionValues(criterionType, criterion);

		//		Boolean yourAnswer = readAwardCriterionAnswer(criterionType);
		//		criterion.setAnswer(yourAnswer);
		//
		//		// description1 is overloaded by multiple fields but it should not be a problem since they are coming from different criteria
		//		String detailsCategory = readRequirementValue(OtherCriterionRequirement.DETAILS_EMPLOYEES_CATEGORY,
		//				criterionType);
		//		if (isNotBlank(detailsCategory)) {
		//			criterion.setDescription1(detailsCategory);
		//		}
		//		String regNumber = readRequirementValue(OtherCriterionRequirement.PROVIDE_REGISTRATION_NUMBER, criterionType);
		//		if (isNotBlank(regNumber)) {
		//			criterion.setDescription1(regNumber);
		//		}
		//		String eoRole = readRequirementValue(OtherCriterionRequirement.ECONOMIC_OPERATOR_ROLE, criterionType);
		//		if (isNotBlank(eoRole)) {
		//			criterion.setDescription1(eoRole);
		//		}
		//		String describe = readRequirementValue(OtherCriterionRequirement.PLEASE_DESCRIBE, criterionType);
		//		if (isNotBlank(describe)) {
		//			criterion.setDescription1(describe);
		//		}
		//		String subcontractors = readRequirementValue(OtherCriterionRequirement.LIST_SUBCONTRACTORS, criterionType);
		//		if (isNotBlank(subcontractors)) {
		//			criterion.setDescription1(subcontractors);
		//		}
		//
		//		String regNumberElectronically = readRequirementValue(OtherCriterionRequirement.REG_NO_AVAILABLE_ELECTRONICALLY,
		//				criterionType);
		//		if (isNotBlank(regNumberElectronically)) {
		//			criterion.setDescription2(regNumberElectronically);
		//		}
		//		String otherEos = readRequirementValue(OtherCriterionRequirement.OTHER_ECONOMIC_OPERATORS, criterionType);
		//		if (isNotBlank(otherEos)) {
		//			criterion.setDescription2(otherEos);
		//		}
		//
		//		String referencesRegistration = readRequirementValue(OtherCriterionRequirement.REFERENCES_REGISTRATION,
		//				criterionType);
		//		if (isNotBlank(referencesRegistration)) {
		//			criterion.setDescription3(referencesRegistration);
		//		}
		//		String participantGroupName = readRequirementValue(OtherCriterionRequirement.PARTICIPANT_GROUP_NAME,
		//				criterionType);
		//		if (isNotBlank(participantGroupName)) {
		//			criterion.setDescription3(participantGroupName);
		//		}
		//
		//		// this field used to contain 'description4' but has become an indicator
		//		Boolean eoProvideCertificate = readRequirementValue(OtherCriterionRequirement.EO_ABLE_PROVIDE_CERTIFICATE,
		//				criterionType);
		//		criterion.setBooleanValue3(eoProvideCertificate);
		//		String docElectronically = readRequirementValue(OtherCriterionRequirement.DOC_AVAILABLE_ELECTRONICALLY,
		//				criterionType);
		//		if (isNotBlank(docElectronically)) {
		//			criterion.setDescription5(docElectronically);
		//		}
		//
		//		Boolean coversAllSelectionCriteria = readBooleanRequirement(
		//				OtherCriterionRequirement.REGISTRATION_COVERS_SELECTION_CRITERIA, criterionType);
		//		criterion.setBooleanValue1(coversAllSelectionCriteria);
		//		BigDecimal percentage = readRequirementValue(OtherCriterionRequirement.CORRESPONDING_PERCENTAGE, criterionType);
		//		criterion.setDoubleValue1(percentage);
		//
		//		criterion.setAvailableElectronically(buildAwardAvailableElectronically(criterionType));

		return criterion;
	}

	private Boolean readBooleanRequirement(CcvCriterionRequirement requirement, CriterionType criterionType) {
		// special case to avoid null pointer exceptions
		return readRequirementValue(requirement, criterionType);
	}

	@SuppressWarnings("unchecked")
	private <T> T readRequirementValue(CcvCriterionRequirement requirement, CriterionType criterionType) {
		if (isEmpty(criterionType.getRequirementGroup())) {
			return null;
		}

		RequirementType requirementType = findRequirementInGroups(requirement, criterionType.getRequirementGroup());
		if (requirementType != null && isNotEmpty(requirementType.getResponse())) {
			return (T) requirement.getResponseType().parseValue(requirementType.getResponse().get(0));
		}

		return null;
	}

	private RequirementType findRequirementInGroup(CcvCriterionRequirement requirement,
			RequirementGroupType requirementGroup) {
		if (isNotEmpty(requirementGroup.getRequirement())) {
			for (RequirementType requirementType : requirementGroup.getRequirement()) {
				if (requirementType.getID() != null && requirement.getId().equals(requirementType.getID().getValue())) {
					return requirementType;
				}
			}
		}

		return findRequirementInGroups(requirement, requirementGroup.getRequirementGroup());
	}

	private RequirementType findRequirementInGroups(CcvCriterionRequirement requirement,
			List<RequirementGroupType> requirementGroups) {
		if (isEmpty(requirementGroups)) {
			return null;
		}

		for (RequirementGroupType group : requirementGroups) {
			RequirementType found = findRequirementInGroup(requirement, group);
			if (found != null) {
				return found;
			}
		}

		return null;
	}

}
