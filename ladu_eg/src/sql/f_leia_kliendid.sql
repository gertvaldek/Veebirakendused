CREATE OR REPLACE FUNCTION f_leia_kliendid(Price_list.price_list%TYPE) RETURNS TABLE 
(kood numeric, klient varchar) AS
$$
SELECT R.kood, R.klient FROM (
SELECT CPL.customer_fk AS kood,(P.first_name || ' ' || P.last_name) AS klient FROM customer_price_list CPL
LEFT JOIN customer C ON CPL.customer_fk= C.customer
INNER JOIN person P ON C.subject_fk = P.person
LEFT JOIN price_list PL ON CPL.price_list_fk= PL.price_list
WHERE C.subject_type_fk=1 AND CPL.price_list_fk=$1
UNION
SELECT CPL.customer_fk AS kood, E.name AS klient FROM customer_price_list CPL
LEFT JOIN customer C ON CPL.customer_fk= C.customer
INNER JOIN enterprise E ON C.subject_fk = E.enterprise
LEFT JOIN price_list PL ON CPL.price_list_fk= PL.price_list
WHERE C.subject_type_fk=2 AND CPL.price_list_fk=$1) AS R
$$ 
LANGUAGE sql SECURITY DEFINER
SET search_path = public, pg_temp;