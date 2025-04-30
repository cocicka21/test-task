INSERT INTO public.users
    (id, date_of_birth, name, password)
VALUES
    (1, '1991-1-1', 'Roman', '$2a$12$PsCIKUoR2bv3tdh5TH.VaemXgdSYnFj2yg3PlTPsj.UuEVvM8H8Uq'), --qwerty
    (2, '1999-05-21', 'Igor', '$2a$12$IxDaz2faVHucsmChHBeat.2cBGv1sPmLaKeZVzJvQvKAVoVr.AbFW'), --asdfgh
    (3, '1993-08-15', 'Anna', '$2a$12$LHRLsU.fjAfI82lbaIClMeTB93JSbOb.WpsgEaSZXj.gEVs2zVr3O'); --zxcvbn

INSERT INTO public.accounts
    (id, user_id, balance, initial_balance)
VALUES
    (1, 1, 100.0, 100),
    (2, 2, 100.0, 100),
    (3, 3, 100.0, 100);

INSERT INTO public.email_data
    (id, user_id, email)
VALUES
    (1, 1, 'Roman@example.com'),
    (2, 2, 'Igor@example.com'),
    (3, 3, 'Anna@example.com');

INSERT INTO public.phone_data
    (id, user_id, phone)
VALUES
    (1, 1, '79207865432'),
    (2, 2, '79317167854'),
    (3, 3, '79418976543');

 SELECT setval(pg_get_serial_sequence('public.users', 'id'), coalesce(MAX(id), 1))
from public.users;

 SELECT setval(pg_get_serial_sequence('public.accounts', 'id'), coalesce(MAX(id), 1))
from public.accounts;

 SELECT setval(pg_get_serial_sequence('public.email_data', 'id'), coalesce(MAX(id), 1))
from public.email_data;

 SELECT setval(pg_get_serial_sequence('public.phone_data', 'id'), coalesce(MAX(id), 1))
from public.phone_data;