export enum Role {
    EXPERTISE_MANAGER = "EXPERTISE_MANAGER",
    SENIOR_MANAGER = "SENIOR_MANAGER",
    COMPANY_ADMINISTRATOR = "COMPANY_ADMINISTRATOR",
    CONSULTANT = "CONSULTANT",
}

export const extendedRoles = [Role.EXPERTISE_MANAGER, Role.COMPANY_ADMINISTRATOR];

export const adminOnly = [Role.COMPANY_ADMINISTRATOR, Role.SENIOR_MANAGER];
