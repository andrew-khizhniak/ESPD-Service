package eu.europa.ec.grow.espd.business;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

@SuppressWarnings("unchecked")
public class CriteriaTemplates {

    public static final int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    public static final Integer[] lastYearsAmount = new Integer[]{year, year-1, year-2, year-3, year-4};
    public static final Integer[] lastYearsNumber = new Integer[]{year, year-1, year-2};
    
    /* EXCLUSION CA */
    
    @SuppressWarnings("rawtypes")
	public static final Map[] criminalListCA = new Map[]{
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","criminalConvictions").
	    put("title_code","crit_eu_title_grounds_criminal_conv").
	    put("description_code","crit_eu_text_grounds_criminal_conv").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","corruption").
	    put("title_code","crit_eu_title_corruption").
	    put("description_code","crit_eu_text_corruption").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","fraud").
	    put("title_code","crit_eu_title_fraud").
	    put("description_code","crit_eu_text_fraud").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","terroristOffences").
	    put("title_code","crit_eu_title_terrorist").
	    put("description_code","crit_eu_text_terrorist").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","moneyLaundering").
	    put("title_code","crit_eu_title_money_laundering").
	    put("description_code","crit_eu_text_money_laundering").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","childLabour").
	    put("title_code","crit_eu_title_child_labour").
	    put("description_code","crit_eu_text_child_labour").
	    put("is_always_checked","true").
	    put("is_disabled","true").build()
    };

    @SuppressWarnings("rawtypes")
	public static final Map[] taxesListCA = new Map[]{
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","paymentTaxes").
	    put("title_code","crit_eu_title_payment_taxes").
	    put("description_code","crit_eu_text_payment_taxes").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","paymentSocialSecurity").
	    put("title_code","crit_eu_title_payment_social_security").
	    put("description_code","crit_eu_text_payment_social_security").
	    put("is_always_checked","true").
	    put("is_disabled","true").build()
    };

    @SuppressWarnings("rawtypes")
   	public static final Map[] insolvencyListCA = new Map[]{
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","breachingObligationsEnvironmental").
	    put("title_code","crit_eu_title_breaching_obligations_environmental").
	    put("description_code","crit_eu_text_breaching_obligations_environmental").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","breachingObligationsSocial").
	    put("title_code","crit_eu_title_breaching_obligations_social").
	    put("description_code","crit_eu_text_breaching_obligations_social").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","breachingObligationsLabour").
	    put("title_code","crit_eu_title_breaching_obligations_labour").
	    put("description_code","crit_eu_text_breaching_obligations_labour").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","bankruptcy").
	    put("title_code","crit_eu_title_bankrupt").
	    put("description_code","crit_eu_text_bankrupt").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","insolvency").
	    put("title_code","crit_eu_title_insolvency").
	    put("description_code","crit_eu_text_insolvency").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","arrangementWithCreditors").
	    put("title_code","crit_eu_title_arrangement_creditors").
	    put("description_code","crit_eu_text_arrangement_creditors").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","analogousSituation").
	    put("title_code","crit_eu_title_national_bankruptcy").
	    put("description_code","crit_eu_text_national_bankruptcy").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","assetsAdministeredByLiquidator").
	    put("title_code","crit_eu_title_liquidator").
	    put("description_code","crit_eu_text_liquidator").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","businessActivitiesSuspended").
	    put("title_code","crit_eu_title_suspended_business").
	    put("description_code","crit_eu_text_suspended_business").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","guiltyGrave").
	    put("title_code","crit_eu_title_guilty_misconduct").
	    put("description_code","crit_eu_text_guilty_misconduct").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","agreementsWithOtherEO").
	    put("title_code","crit_eu_title_agreement_economic").
	    put("description_code","crit_eu_text_agreement_economic").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","conflictInterest").
	    put("title_code","crit_eu_title_conflict_interest").
	    put("description_code","crit_eu_text_conflict_interest").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","involvementPreparationProcurement").
	    put("title_code","crit_eu_title_involvement").
	    put("description_code","crit_eu_text_involvement").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","earlyTermination").
	    put("title_code","crit_eu_title_early_termination").
	    put("description_code","crit_eu_text_early_termination").
	    put("is_always_checked","true").
	    put("is_disabled","true").build(),

	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","guiltyMisinterpretation").
	    put("title_code","crit_eu_title_guilty_misinterpretation").
	    put("description_code","crit_eu_text_guilty_misinterpretation").
	    put("is_always_checked","true").
	    put("is_disabled","true").build()
    };
    
    /* EXCLUSION EO */
    
    @SuppressWarnings("rawtypes")
    public static final Map[] criminalListEO = new Map[]{
    	ImmutableMap.<String,String>builder().
    	put("template","criminalFormTemplate").
    	put("field","criminalConvictions").
    	put("title_code","crit_eu_title_grounds_criminal_conv").
    	put("description_code","crit_eu_text_grounds_criminal_conv").build(),

    	ImmutableMap.<String,String>builder().
    	put("template","criminalFormTemplate").
    	put("field","corruption").
    	put("title_code","crit_eu_title_corruption").
    	put("description_code","crit_eu_text_corruption").build(),

    	ImmutableMap.<String,String>builder().
    	put("template","criminalFormTemplate").
    	put("field","fraud").
    	put("title_code","crit_eu_title_fraud").
    	put("description_code","crit_eu_text_fraud").build(),

    	ImmutableMap.<String,String>builder().
    	put("template","criminalFormTemplate").
    	put("field","terroristOffences").
    	put("title_code","crit_eu_title_terrorist").
    	put("description_code","crit_eu_text_terrorist").build(),

    	ImmutableMap.<String,String>builder().
    	put("template","criminalFormTemplate").
    	put("field","moneyLaundering").
    	put("title_code","crit_eu_title_money_laundering").
    	put("description_code","crit_eu_text_money_laundering").build(),

    	ImmutableMap.<String,String>builder().
    	put("template","criminalFormTemplate").
    	put("field","childLabour").
    	put("title_code","crit_eu_title_child_labour").
    	put("description_code","crit_eu_text_child_labour").build()
    };
    
    @SuppressWarnings("rawtypes")
    public static final Map[] taxesListEO = new Map[]{
    	ImmutableMap.<String,String>builder().
    	put("template","taxFormTemplate").
    	put("field","paymentTaxes").
    	put("title_code","crit_eu_title_payment_taxes").
    	put("description_code","crit_eu_text_payment_taxes").
    	put("selfCleaning","false").build(),

    	ImmutableMap.<String,String>builder().
    	put("template","taxFormTemplate").
    	put("field","paymentSocialSecurity").
    	put("title_code","crit_eu_title_payment_social_security").
    	put("description_code","crit_eu_text_payment_social_security").
    	put("selfCleaning","false").build(),
    };
    
    @SuppressWarnings("rawtypes")
    public static final Map[] insolvencyListEO = new Map[]{
		ImmutableMap.<String,String>builder().
		put("template","exclusionFormTemplate").
		put("field","breachingObligationsEnvironmental").
		put("title_code","crit_eu_title_breaching_obligations_environmental").
		put("description_code","crit_eu_text_breaching_obligations_environmental").
		put("availableElectronically","false").build(),
		
		ImmutableMap.<String,String>builder().
		put("template","exclusionFormTemplate").
		put("field","breachingObligationsSocial").
		put("title_code","crit_eu_title_breaching_obligations_social").
		put("description_code","crit_eu_text_breaching_obligations_social").
		put("availableElectronically","false").build(),
		
		ImmutableMap.<String,String>builder().
		put("template","exclusionFormTemplate").
		put("field","breachingObligationsLabour").
		put("title_code","crit_eu_title_breaching_obligations_labour").
		put("description_code","crit_eu_text_breaching_obligations_labour").
		put("availableElectronically","false").build(),
		
		ImmutableMap.<String,String>builder().
		put("template","reasonsNeverlessPerformForm").
		put("field","bankruptcy").
		put("title_code","crit_eu_title_bankrupt").
		put("description_code","crit_eu_text_bankrupt").
		put("selfCleaning","false").build(),
		
		ImmutableMap.<String,String>builder().
		put("template","reasonsNeverlessPerformForm").
		put("field","insolvency").
		put("title_code","crit_eu_title_insolvency").
		put("description_code","crit_eu_text_insolvency").
		put("selfCleaning","false").build(),
		
		ImmutableMap.<String,String>builder().
		put("template","reasonsNeverlessPerformForm").
		put("field","arrangementWithCreditors").
		put("title_code","crit_eu_title_arrangement_creditors").
		put("description_code","crit_eu_text_arrangement_creditors").
		put("selfCleaning","false").build(),
		
		ImmutableMap.<String,String>builder().
		put("template","reasonsNeverlessPerformForm").
		put("field","analogousSituation").
		put("title_code","crit_eu_title_national_bankruptcy").
		put("description_code","crit_eu_text_national_bankruptcy").
		put("selfCleaning","false").build(),
		
		ImmutableMap.<String,String>builder().
		put("template","reasonsNeverlessPerformForm").
		put("field","assetsAdministeredByLiquidator").
		put("title_code","crit_eu_title_liquidator").
		put("description_code","crit_eu_text_liquidator").
		put("selfCleaning","false").build(),
		
		ImmutableMap.<String,String>builder().
		put("template","reasonsNeverlessPerformForm").
		put("field","businessActivitiesSuspended").
		put("title_code","crit_eu_title_suspended_business").
		put("description_code","crit_eu_text_suspended_business").
		put("selfCleaning","false").build(),
		
		ImmutableMap.<String,String>builder().
		put("template","exclusionFormTemplate").
		put("field","agreementsWithOtherEO").
		put("title_code","crit_eu_title_agreement_economic").
		put("description_code","crit_eu_text_agreement_economic").
		put("availableElectronically","false").build(),
		
		ImmutableMap.<String,String>builder().
		put("template","exclusionFormTemplate").
		put("field","guiltyGrave").
		put("title_code","crit_eu_title_guilty_misconduct").
		put("description_code","crit_eu_text_guilty_misconduct").
		put("availableElectronically","false").build(),
		
		ImmutableMap.<String,String>builder().
		put("template","exclusionFormTemplate").
		put("field","conflictInterest").
		put("title_code","crit_eu_title_conflict_interest").
		put("description_code","crit_eu_text_conflict_interest").build(),
		
		ImmutableMap.<String,String>builder().
		put("template","exclusionFormTemplate").
		put("field","involvementPreparationProcurement").
		put("title_code","crit_eu_title_involvement").
		put("description_code","crit_eu_text_involvement").
		put("availableElectronically","false").
		put("selfCleaning","false").build(),
		
		ImmutableMap.<String,String>builder().
		put("template","exclusionFormTemplate").
		put("field","earlyTermination").
		put("title_code","crit_eu_title_early_termination").
		put("description_code","crit_eu_text_early_termination").
		put("availableElectronically","false").build(),
		
		ImmutableMap.<String,String>builder().
		put("template","exclusionFormTemplate").
		put("field","guiltyMisinterpretation").
		put("title_code","crit_eu_title_guilty_misinterpretation").
		put("description_code","crit_eu_text_guilty_misinterpretation").
		put("selfCleaning","false").build()

    };

    /* SELECTION CA */
    
    @SuppressWarnings("rawtypes")
	public static final Map[] suitabilityListCA = new Map[]{
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","enrolmentProfessionalRegister").
	    put("title_code","crit_selection_suitability_enrolment_professional_register_main").
	    put("description_code","crit_eu_text_guilty_misinterpretation").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","enrolmentTradeRegister").
	    put("title_code","crit_selection_suitability_enrolment_trade_register_main").
	    put("description_code","crit_selection_suitability_enrolment_trade_register_description").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","serviceContractsAuthorisation").
	    put("title_code","crit_selection_suitability_service_contracts_auth_main").
	    put("description_code","crit_selection_suitability_service_contracts_auth_description").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","serviceContractsMembership").
	    put("title_code","crit_selection_suitability_service_contracts_membership_main").
	    put("description_code","crit_selection_suitability_service_contracts_membership_description").build()
    };

    @SuppressWarnings("rawtypes")
   	public static final Map[] economicListCA = new Map[]{
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","generalYearlyTurnover").
	    put("title_code","crit_selection_economic_general_yearly_turnover_main").
	    put("description_code","crit_selection_economic_general_yearly_turnover_description").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","averageYearlyTurnover").
	    put("title_code","crit_selection_economic_average_yearly_turnover_main").
	    put("description_code","crit_selection_economic_average_yearly_turnover_description").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","specificYearlyTurnover").
	    put("title_code","crit_selection_economic_specific_yearly_turnover_main").
	    put("description_code","crit_selection_economic_specific_yearly_turnover_description").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","specificAverageTurnover").
	    put("title_code","crit_selection_economic_specific_average_turnover_main").
	    put("description_code","crit_selection_economic_specific_average_turnover_description").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","setupEconomicOperator").
	    put("title_code","crit_selection_economic_setup_eo_main").
	    put("description_code","crit_selection_economic_setup_eo_description").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","financialRatio").
	    put("title_code","crit_selection_economic_financial_ratio_main").
	    put("description_code","crit_selection_economic_financial_ratio_description").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","professionalRiskInsurance").
	    put("title_code","crit_selection_economic_professional_risk_insurance_main").
	    put("description_code","crit_selection_economic_professional_risk_insurance_description").build(),
	    
	    ImmutableMap.<String, Object>builder().
	    put("template","checkTemplate").
	    put("field","otherEconomicFinancialRequirements").
	    put("title_code","crit_selection_economic_other_financial_requirements_main").
	    put("description_code","crit_selection_economic_other_financial_requirements_description").build()
    };
    
    @SuppressWarnings("rawtypes")
   	public static final Map[] technicalListCA = new Map[]{
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","workContractsPerformanceOfWorks").
    	put("title_code","crit_selection_technical_work_contracts_performance_works_main").
    	put("description_code","crit_selection_technical_work_contracts_performance_works_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","supplyContractsPerformanceDeliveries").
    	put("title_code","crit_selection_technical_supply_contracts_performance_deliveries_main").
    	put("description_code","crit_selection_technical_supply_contracts_performance_deliveries_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","serviceContractsPerformanceServices").
    	put("title_code","crit_selection_technical_service_contracts_performance_services_main").
    	put("description_code","crit_selection_technical_service_contracts_performance_services_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","techniciansTechnicalBodies").
    	put("title_code","crit_selection_technical_technicians_technical_bodies_main").
    	put("description_code","crit_selection_technical_technicians_technical_bodies_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","workContractsTechnicians").
    	put("title_code","crit_selection_technical_work_contracts_technicians_main").
    	put("description_code","crit_selection_technical_work_contracts_technicians_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","technicalFacilitiesMeasures").
    	put("title_code","crit_selection_technical_technical_facilities_measures_main").
    	put("description_code","crit_selection_technical_technical_facilities_measures_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","studyResearchFacilities").
    	put("title_code","crit_selection_technical_study_research_facilities_main").
    	put("description_code","crit_selection_technical_study_research_facilities_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","supplyChainManagement").
    	put("title_code","crit_selection_technical_supply_chain_management_main").
    	put("description_code","crit_selection_technical_supply_chain_management_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","allowanceOfChecks").
    	put("title_code","crit_selection_technical_allowance_of_checks_main").
    	put("description_code","crit_selection_technical_allowance_of_checks_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","educationalProfessionalQualifications").
    	put("title_code","crit_selection_technical_educational_professional_qualifications_main").
    	put("description_code","crit_selection_technical_educational_professional_qualifications_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","environmentalManagementFeatures").
    	put("title_code","crit_selection_technical_environment_management_features_main").
    	put("description_code","crit_selection_technical_environment_management_features_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","numberManagerialStaff").
    	put("title_code","crit_selection_technical_number_managerial_staff_main").
    	put("description_code","crit_selection_technical_number_managerial_staff_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","averageAnnualManpower").
    	put("title_code","crit_selection_technical_average_annual_manpower_main").
    	put("description_code","crit_selection_technical_average_annual_manpower_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","toolsPlantTechnicalEquipment").
    	put("title_code","crit_selection_technical_tools_plant_main").
    	put("description_code","crit_selection_technical_tools_plant_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","subcontractingProportion").
    	put("title_code","crit_selection_technical_subcontracting_proportion_main").
    	put("description_code","crit_selection_technical_subcontracting_proportion_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","supplyContractsSamplesDescriptionsWithoutCa").
    	put("title_code","crit_selection_technical_supply_contracts_without_ca_main").
    	put("description_code","crit_selection_technical_supply_contracts_without_ca_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","supplyContractsSamplesDescriptionsWithCa").
    	put("title_code","crit_selection_technical_supply_contracts_with_ca_main").
    	put("description_code","crit_selection_technical_supply_contracts_with_ca_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","supplyContractsCertificatesQc").
    	put("title_code","crit_selection_technical_supply_contracts_certificate_quality_main").
    	put("description_code","crit_selection_technical_supply_contracts_certificate_quality_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","certificateIndependentBodiesAboutQa").
    	put("title_code","crit_selection_technical_certificate_independent_bodies_quality_main").
    	put("description_code","crit_selection_technical_certificate_independent_bodies_quality_description").build(),
    	
    	ImmutableMap.<String, Object>builder().
    	put("template","checkTemplate").
    	put("field","certificateIndependentBodiesAboutEnvironmental").
    	put("title_code","crit_selection_technical_certificate_independent_bodies_environmental_main").
    	put("description_code","crit_selection_technical_certificate_independent_bodies_environmental_description").build()
    };

    /* SELECTION EO */

    @SuppressWarnings("rawtypes")
	public static final Map[] suitabilityListEO = new Map[]{
		ImmutableMap.<String, Object>builder().
		put("template", "selectionFormTemplate").
		put("field", "enrolmentProfessionalRegister").
		put("title_code", "crit_selection_suitability_enrolment_professional_register_main").
		put("description_code", "crit_selection_suitability_enrolment_professional_register_description").build(),

		ImmutableMap.<String, Object>builder().
		put("template", "selectionFormTemplate").
		put("field", "enrolmentTradeRegister").
		put("title_code", "crit_selection_suitability_enrolment_trade_register_main").
		put("description_code", "crit_selection_suitability_enrolment_trade_register_description").build(),

		ImmutableMap.<String, Object>builder().
		put("template", "selectionFormTemplate").
		put("field", "serviceContractsAuthorisation").
		put("title_code", "crit_selection_suitability_service_contracts_auth_main").
		put("description_code", "crit_selection_suitability_service_contracts_auth_description").build(),

		ImmutableMap.<String, Object>builder().
		put("template", "selectionFormTemplate").
		put("field", "serviceContractsMembership").
		put("title_code", "crit_selection_suitability_service_contracts_membership_main").
		put("description_code", "crit_selection_suitability_service_contracts_membership_description").build()
	};

    @SuppressWarnings("rawtypes")
    public static final Map[] economicListEO = new Map[]{
		ImmutableMap.<String,Object>builder().
		put("template","economicFinancialCriterionEO").
		put("field","generalYearlyTurnover").
		put("title_code","crit_selection_economic_general_yearly_turnover_main").
		put("description_code","crit_selection_economic_general_yearly_turnover_description").
		put("lastYearsAmount",lastYearsAmount).build(),

		ImmutableMap.<String, Object>builder().
		put("template","economicFinancialCriterionEO").
		put("field","averageYearlyTurnover").
		put("title_code","crit_selection_economic_average_yearly_turnover_main").
		put("description_code","crit_selection_economic_average_yearly_turnover_description").
		put("lastYearsAmount",lastYearsAmount).build(),

		ImmutableMap.<String, Object>builder().
		put("template","economicFinancialCriterionEO").
		put("field","specificYearlyTurnover").
		put("title_code","crit_selection_economic_specific_yearly_turnover_main").
		put("description_code","crit_selection_economic_specific_yearly_turnover_description").
		put("lastYearsAmount",lastYearsAmount).build(),

		ImmutableMap.<String, Object>builder().
		put("template","economicFinancialCriterionEO").
		put("field","specificAverageTurnover").
		put("title_code","crit_selection_economic_specific_average_turnover_main").
		put("description_code","crit_selection_economic_specific_average_turnover_description").
		put("lastYearsAmount",lastYearsAmount).build(),

		ImmutableMap.<String, Object>builder().
		put("template","economicFinancialCriterionEO").
		put("field","setupEconomicOperator").
		put("title_code","crit_selection_economic_setup_eo_main").
		put("description_code","crit_selection_economic_setup_eo_description").
		put("has_specify_year","true").
		put("availableElectronically","false").
		put("has_multiple_year_amount","false").
		put("lastYearsAmount",lastYearsAmount).build(),

		ImmutableMap.<String, Object>builder().
		put("template","economicFinancialCriterionEO").
		put("field","financialRatio").
		put("title_code","crit_selection_economic_financial_ratio_main").
		put("description_code","crit_selection_economic_financial_ratio_description").
		put("has_multiple_description_ratio","true").build(),
		                        
		ImmutableMap.<String, Object>builder().
		put("template","economicFinancialCriterionEO").
		put("field","professionalRiskInsurance").
		put("title_code","crit_selection_economic_professional_risk_insurance_main").
		put("description_code","crit_selection_economic_professional_risk_insurance_description").
		put("has_single_amount","true").build(),

		ImmutableMap.<String, Object>builder().
		put("template","economicFinancialCriterionEO").
		put("field","otherEconomicFinancialRequirements").
		put("title_code","crit_selection_economic_other_financial_requirements_main").
		put("description_code","crit_selection_economic_other_financial_requirements_description").
		put("has_please_describe_them","true").build()
    };

    @SuppressWarnings("rawtypes")
    public static final Map[] technicalListEO = new Map[]{
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","workContractsPerformanceOfWorks").
		put("has_multiple_description_amount_date_recipients","true").
		put("title_code","crit_selection_technical_work_contracts_performance_works_main").
		put("description_code","crit_selection_technical_work_contracts_performance_works_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","supplyContractsPerformanceDeliveries").
		put("has_multiple_description_amount_date_recipients","true").
		put("title_code","crit_selection_technical_supply_contracts_performance_deliveries_main").
		put("description_code","crit_selection_technical_supply_contracts_performance_deliveries_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","serviceContractsPerformanceServices").
		put("has_multiple_description_amount_date_recipients","true").
		put("title_code","crit_selection_technical_service_contracts_performance_services_main").
		put("description_code","crit_selection_technical_service_contracts_performance_services_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","techniciansTechnicalBodies").
		put("has_please_describe_them","true").
		put("title_code","crit_selection_technical_technicians_technical_bodies_main").
		put("description_code","crit_selection_technical_technicians_technical_bodies_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","workContractsTechnicians").
		put("has_please_describe_them","true").
		put("title_code","crit_selection_technical_work_contracts_technicians_main").
		put("description_code","crit_selection_technical_work_contracts_technicians_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","technicalFacilitiesMeasures").
		put("has_please_describe_them","true").
		put("title_code","crit_selection_technical_technical_facilities_measures_main").
		put("description_code","crit_selection_technical_technical_facilities_measures_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","studyResearchFacilities").
		put("has_please_describe_them","true").
		put("title_code","crit_selection_technical_study_research_facilities_main").
		put("description_code","crit_selection_technical_study_research_facilities_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","supplyChainManagement").
		put("has_please_describe_them","true").
		put("title_code","crit_selection_technical_supply_chain_management_main").
		put("description_code","crit_selection_technical_supply_chain_management_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","allowanceOfChecks").
		put("has_your_answer","true").
		put("allows_checks","true").
		put("availableElectronically","false").
		put("title_code","crit_selection_technical_allowance_of_checks_main").
		put("description_code","crit_selection_technical_allowance_of_checks_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","educationalProfessionalQualifications").
		put("has_please_describe_them","true").
		put("title_code","crit_selection_technical_educational_professional_qualifications_main").
		put("description_code","crit_selection_technical_educational_professional_qualifications_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","environmentalManagementFeatures").
		put("has_please_describe_them","true").
		put("title_code","crit_selection_technical_environment_management_features_main").
		put("description_code","crit_selection_technical_environment_management_features_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","numberManagerialStaff").
		put("lastYearsNumber",lastYearsNumber).
		put("title_code","crit_selection_technical_number_managerial_staff_main").
		put("description_code","crit_selection_technical_number_managerial_staff_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","averageAnnualManpower").
		put("lastYearsNumber",lastYearsNumber).
		put("title_code","crit_selection_technical_average_annual_manpower_main").
		put("description_code","crit_selection_technical_average_annual_manpower_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","toolsPlantTechnicalEquipment").
		put("has_please_describe_them","true").
		put("title_code","crit_selection_technical_tools_plant_main").
		put("description_code","crit_selection_technical_tools_plant_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","subcontractingProportion").
		put("has_please_specify","true").
		put("availableElectronically","false").
		put("title_code","crit_selection_technical_subcontracting_proportion_main").
		put("description_code","crit_selection_technical_subcontracting_proportion_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","supplyContractsSamplesDescriptionsWithoutCa").
		put("has_your_answer","true").
		put("title_code","crit_selection_technical_supply_contracts_without_ca_main").
		put("description_code","crit_selection_technical_supply_contracts_without_ca_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","supplyContractsSamplesDescriptionsWithCa").
		put("has_your_answer","true").
		put("title_code","crit_selection_technical_supply_contracts_with_ca_main").
		put("description_code","crit_selection_technical_supply_contracts_with_ca_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","supplyContractsCertificatesQc").
		put("has_your_answer","true").
		put("has_please_describe_them","true").
		put("has_explain_supply_contracts_quality","true").
		put("title_code","crit_selection_technical_supply_contracts_certificate_quality_main").
		put("description_code","crit_selection_technical_supply_contracts_certificate_quality_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","certificateIndependentBodiesAboutQa").
		put("has_your_answer","true").
		put("has_please_describe_them","true").
		put("has_explain_certificates_independent_quality","true").
		put("title_code","crit_selection_technical_certificate_independent_bodies_quality_main").
		put("description_code","crit_selection_technical_certificate_independent_bodies_quality_description").build(),
		
		ImmutableMap.<String, Object>builder().
		put("template","technicalProfessionalCriterionEO").
		put("field","certificateIndependentBodiesAboutEnvironmental").
		put("has_your_answer","true").
		put("has_please_describe_them","true").
		put("has_explain_certificates_independent_environmental","true").
		put("title_code","crit_selection_technical_certificate_independent_bodies_environmental_main").
		put("description_code","crit_selection_technical_certificate_independent_bodies_environmental_description").build()
	};

    @SuppressWarnings("rawtypes")
    public static Map[] technicalListEO_UglyPrintVersion = new Map[technicalListEO.length];
    static {
    	for(int i = 0 ; i < technicalListEO_UglyPrintVersion.length; i++) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.putAll(technicalListEO[i]);
    		if(i == 0 || i == 1 || i == 2) {
    			map.put("template","uglyPrintTemplate");
    		}
    		technicalListEO_UglyPrintVersion[i] = map;
    	}
    };

}