CREATE FUNCTION f_uuenda_lao_hinda (item.item%TYPE, item_action.item_count%TYPE, item_action.action_price%TYPE) 
RETURNS void AS $$

DECLARE 
toote_arv_enne_lisamist_ladudes_kokku numeric(10,2);
toodete_arv_peale_lisamist numeric(10,2);
toodte_laohind_ennem_lisamist numeric(10,2);
uus_lao_hind numeric(10,2);

BEGIN
toote_arv_enne_lisamist_ladudes_kokku = (SELECT SUM(item_count) FROM item_store WHERE item_fk = $1);
toodte_laohind_ennem_lisamist = (SELECT store_price FROM item where item = $1);
toodete_arv_peale_lisamist = toote_arv_enne_lisamist_ladudes_kokku + $2;

uus_lao_hind = (($2 * $3) + (toote_arv_enne_lisamist_ladudes_kokku * toodte_laohind_ennem_lisamist)) / toodete_arv_peale_lisamist;

UPDATE item SET store_price=uus_lao_hind WHERE item = $1;
END;
$$
LANGUAGE plpgsql SECURITY DEFINER
SET search_path = public, pg_temp;