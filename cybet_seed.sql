insert into user_account_types (id, ac_type_name, description, created, updated, deleted) values (1, 'ROOT', 'Root Account', now(), now(), 'NO');
insert into user_account_types (id, ac_type_name, description, created, updated, deleted) values (2, 'BOOKMAKER', 'Bookmaker Account', now(), now(), 'NO');
insert into user_account_types (id, ac_type_name, description, created, updated, deleted) values (3, 'STANDARD USER', 'Standard user Account', now(), now(), 'NO');
--
insert into user_accounts (id, first_name, last_name, email_address, activated, user_account_type_id, ethereum_wallet_address, completed_kyc, user_password, created, updated, deleted) values (1, 'Root', 'Toor', 'root@cybet.club', 'YES', 1, '0x000000000000000000000', 'YES', 't8F+UB6sa+yQsP3/rd3pCFgPIQ8WtI4DC6zEe8VI1Pk=$N8x86SDrXEIkBQ7UzjXWyEfEpbHJFaZcmAqKCCxcyHA=', now(), now(), 'NO');
--
insert into identification_types (id, identification_code, identification_name, created, updated, deleted) values (1, 'ID', 'National Identification Card', now(), now(), 'NO');
insert into identification_types (id, identification_code, identification_name, created, updated, deleted) values (2, 'License', 'Drivers License', now(), now(), 'NO');
insert into identification_types (id, identification_code, identification_name, created, updated, deleted) values (3, 'Passport', 'National Passport', now(), now(), 'NO');
insert into identification_types (id, identification_code, identification_name, created, updated, deleted) values (4, 'DP', 'Photo / Selfie', now(), now(), 'NO');
--
insert into attachment_types (id, attachment_type_name, description, created, updated, deleted) values (1, 'PROFILE PICTURE', 'User account display picture', now(), now(), 'NO');
insert into attachment_types (id, attachment_type_name, description, created, updated, deleted) values (2, 'IDENTITY DOCUMENT FRONT', 'Legal identity document for user (Front)', now(), now(), 'NO');
insert into attachment_types (id, attachment_type_name, description, created, updated, deleted) values (3, 'IDENTITY DOCUMENT BACK', 'Legal identity document for user (Back)', now(), now(), 'NO');
--
insert into employment_industries (id, employment_industry_name, description, created, updated, deleted) values (1, 'Self Employed', 'Self employed', now(), now(), 'NO');
insert into employment_industries (id, employment_industry_name, description, created, updated, deleted) values (2, 'Information Technology', 'IT', now(), now(), 'NO');
insert into employment_industries (id, employment_industry_name, description, created, updated, deleted) values (3, 'Finance', 'Finance', now(), now(), 'NO');
insert into employment_industries (id, employment_industry_name, description, created, updated, deleted) values (4, 'Education', 'Education', now(), now(), 'NO');
insert into employment_industries (id, employment_industry_name, description, created, updated, deleted) values (5, 'Agriculture', 'Agriculture', now(), now(), 'NO');
insert into employment_industries (id, employment_industry_name, description, created, updated, deleted) values (6, 'Transport', 'Transport', now(), now(), 'NO');
insert into employment_industries (id, employment_industry_name, description, created, updated, deleted) values (7, 'Religion', 'Religion', now(), now(), 'NO');
insert into employment_industries (id, employment_industry_name, description, created, updated, deleted) values (8, 'Business / Entrepreneurial', 'Business / Entrepreneurial', now(), now(), 'NO');
insert into employment_industries (id, employment_industry_name, description, created, updated, deleted) values (9, 'Medicine', 'Medicine', now(), now(), 'NO');
insert into employment_industries (id, employment_industry_name, description, created, updated, deleted) values (10, 'Engineering', 'Engineering', now(), now(), 'NO');
insert into employment_industries (id, employment_industry_name, description, created, updated, deleted) values (11, 'Professional', 'Professional', now(), now(), 'NO');
insert into employment_industries (id, employment_industry_name, description, created, updated, deleted) values (12, 'Other', 'Other', now(), now(), 'NO');
--
insert into employment_status (id, employment_status_name, description, created, updated, deleted) values(1, 'UNEMPLOYED', 'Unemployed', now(), now(), 'NO');
insert into employment_status (id, employment_status_name, description, created, updated, deleted) values(2, 'SELF EMPLOYED', 'Self employed', now(), now(), 'NO');
--
insert into job_titles (id, job_title_name, description, created, updated, deleted) values (1, 'Executive', 'Executive', now(), now(), 'NO');
insert into job_titles (id, job_title_name, description, created, updated, deleted) values (2, 'Senior Staff', 'Senior Staff', now(), now(), 'NO');
insert into job_titles (id, job_title_name, description, created, updated, deleted) values (3, 'Staff', 'Staff', now(), now(), 'NO');
insert into job_titles (id, job_title_name, description, created, updated, deleted) values (4, 'Junior Staff', 'Junior Staff', now(), now(), 'NO');
insert into job_titles (id, job_title_name, description, created, updated, deleted) values (5, 'Other', 'Other', now(), now(), 'NO');
--
insert into self_annual_income_brackets (id, self_annual_income_bracket_name, description, created, updated, deleted) values (1, 'Below $100 Per month', 'Below 100 USD per month', now(), now(), 'NO');
insert into self_annual_income_brackets (id, self_annual_income_bracket_name, description, created, updated, deleted) values (2, '$100 - $1,000', '100 - 1,000 USD per month', now(), now(), 'NO');
insert into self_annual_income_brackets (id, self_annual_income_bracket_name, description, created, updated, deleted) values (3, '$1,001 - $100,000', '1,000 - 100,000 USD per month', now(), now(), 'NO');
insert into self_annual_income_brackets (id, self_annual_income_bracket_name, description, created, updated, deleted) values (4, 'Above $100,000', 'Above 100,000 USD per month', now(), now(), 'NO');
--
insert into source_of_funds (id, source_of_funds_name, description, created, updated, deleted) values (1, 'Self Employment', 'Self employment', now(), now(), 'NO');
insert into source_of_funds (id, source_of_funds_name, description, created, updated, deleted) values (2, 'Employment Only', 'Employment Only', now(), now(), 'NO');
insert into source_of_funds (id, source_of_funds_name, description, created, updated, deleted) values (3, 'Employment & Investments', 'Employment and other investments', now(), now(), 'NO');
insert into source_of_funds (id, source_of_funds_name, description, created, updated, deleted) values (4, 'Employment & Crypto-currency investments', 'Employment and crypto-currency investments', now(), now(), 'NO');