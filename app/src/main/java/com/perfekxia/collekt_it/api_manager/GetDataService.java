package com.perfekxia.collekt_it.api_manager;

import com.perfekxia.collekt_it.model.AdminPins;
import com.perfekxia.collekt_it.model.Affectations;
import com.perfekxia.collekt_it.model.Clients;
import com.perfekxia.collekt_it.model.Collectes;
import com.perfekxia.collekt_it.model.Comptes;
import com.perfekxia.collekt_it.model.Cycles;
import com.perfekxia.collekt_it.model.Equipements;
import com.perfekxia.collekt_it.model.Mises;
import com.perfekxia.collekt_it.model.Produits;
import com.perfekxia.collekt_it.model.Retraits;
import com.perfekxia.collekt_it.model.Titulaires;
import com.perfekxia.collekt_it.model.Utilisateurs;
import com.perfekxia.collekt_it.model.Zones;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GetDataService {

    /*
        AdminPin
     */
    @GET("api/AdminPins/pin/{pin}")
    Call<AdminPins> getPin(@Path("pin") Integer pin);

    /*
        Affectation
     */
    @GET("api/Affectations")
    Call<List<Affectations>> listAffectation();

    @GET("api/Affectations/{id}")
    Call<Affectations> getAffectationById(@Path("id") String id);

    @POST("api/Affectations")
    Call<Affectations> saveAffectation(@Body Affectations affectations);

    @PUT("api/Affectations/{id}")
    Call<Affectations> updateAffectation(@Path("id") String id, @Body Affectations affectations);

    @DELETE("api/Affectations/{id}")
    Call<Affectations> deleteAffectation(@Path("id") String id);

    @GET("api/Affectations/zone/{idTitulaire}")
    Call<Zones> getAffectationZone(@Path("idTitulaire") String idTitulaire);

    @GET("api/Affectations/UpdateAll")
    Call updateAllAffectation();

    /*
        Clients
     */
    @GET("api/Clients")
    Call<List<Clients>> listClients();

    @GET("api/Clients/{id}")
    Call<Clients> getClientById(@Path("id") String id);

    @POST("api/Clients")
    Call<Clients> saveClient(@Body Clients client);

    @PUT("api/Clients/{id}")
    Call<Clients> updateClient(@Path("id") String id, @Body Clients client);

    @DELETE("api/Clients/{id}")
    Call<Clients> deleteClient(@Path("id") String id);

    @GET("api/Clients/ClientsByZone/{idZone}")
    Call<List<Clients>> getClientZone(@Path("idZone") String idZone);

    @GET("api/Clients/RecoveredClientsByZone/{idZone}")
    Call<List<Clients>> getClientRecoveredByZone(@Path("idZone") String idZone);

    @GET("api/Clients/UpdateAll")
    Call updateAllClient();

    /*
        Collecte
     */
    @GET("api/Collectes")
    Call<List<Collectes>> listCollecte();

    @GET("api/Collectes/{id}")
    Call<Collectes> getCollecteById(@Path("id") String id);

    @POST("api/Collectes")
    Call<Collectes> saveCollecte(@Body Collectes collecte);

    @PUT("api/Collectes/{id}")
    Call<Collectes> updateCollecte(@Path("id") String id, @Body Collectes collecte);

    @DELETE("api/Collectes/{id}")
    Call<Collectes> deleteCollecte(@Path("id") String id);

//    @GET("api/Collectes/CollectesByCycle/{idCycle}")
//    Call<List<Collectes>> getCollecteByCycle(@Path("idCycle") String idCycle);
//
//    @GET("api/Collectes/RecoveredCollectesByCycle/idCycle")
//    Call<List<Collectes>> getCollecteRecoveredByCycle(@Path("idCycle") String idCycle);

    @GET("api/Collectes/UpdateAll")
    Call updateAllCollecte();

    @GET(" api/Collectes/AmountByCycle/{idCycle}")
    Call<Integer> amountByCycleCollectes(@Path("idCycle") String idCycle);

    /*
        Compte
     */
    @GET("api/Comptes")
    Call<List<Comptes>> listCompte();

    @GET("api/Comptes/{id}")
    Call<Comptes> getCompteById(@Path("id") String id);

    @POST("api/Comptes")
    Call<Comptes> saveCompte(@Body Comptes compte);

    @PUT("api/Comptes/{id}")
    Call<Comptes> updateCompte(@Path("id") String id, @Body Comptes compte);

    @DELETE("api/Comptes/{id}")
    Call<Comptes> deleteCompte(@Path("id") String id);

    @GET("api/Comptes/AccountsByClient/{idClient}")
    Call<List<Comptes>> getCompteByClient(@Path("idClient") String idClient);

    @GET("api/Comptes/RecoveredAccountsByClient/{idClient}")
    Call<List<Comptes>> getCompteRecoveredByClient(@Path("idClient") String idClient);

    @GET("api/Comptes/UpdateAll")
    Call updateAllCompte();

    @GET("api/comptes/AccountsByProduct/{idProduit}")
    Call<List<Comptes>> accountsByProducts(@Path("idProduit") String idProduit);

    /*
        Cycles
     */
    @GET("api/Cycles")
    Call<List<Cycles>> listCycle();

    @GET("api/Cycles/{id}")
    Call<Cycles> getCycleById(@Path("id") String id);

    @POST("api/Cycles")
    Call<Cycles> saveCycle(@Body Cycles cycle);

    @PUT("api/Cycles/{id}")
    Call<Cycles> updateCycle(@Path("id") String id, @Body Cycles cycle);

    @DELETE("api/Cycles/{id}")
    Call<Cycles> deleteCycle(@Path("id") String id);

    @GET("api/Cycles/ActiveCycleByAccount/{idCompte}")
    Call<List<Cycles>> getActiveCycleByCompte(@Path("idCompte") String idCompte);

    @GET("api/Cycles/ActiveCycle/{idCompte}")
    Call<List<Cycles>> getActiveCycle(@Path("idCompte") String idCompte);

    @GET("api/Cycles/RecoveredActiveCycleByAccount/{idCompte}")
    Call<List<Cycles>> getActiveCycleRecoveredByCompte(@Path("idCompte") String idCompte);


    /*
        Equipements
     */
    @GET("api/Equipements")
    Call<List<Equipements>> listEquipement();

    @GET("api/Equipements/{id}")
    Call<Equipements> getEquipementById(@Path("id") String id);

    @POST("api/Equipements")
    Call<Equipements> saveEquipement(@Body Equipements equipement);

    @PUT("api/Equipements/{id}")
    Call<Equipements> updateEquipement(@Path("id") int id, @Body Equipements equipement);

    @DELETE("api/Equipements/{id}")
    Call<Equipements> deleteEquipement(@Path("id") String id);

    @GET("api/Equipements/Exists/{id}")
    Call<Boolean> checkEquipementExist(@Path("id") String id);

    @GET("api/Equipements/InUse/{id}")
    Call<Boolean> checkEquipementUse(@Path("id") String id);

    @GET("api/Equipements/ConfirmTitulaire/{idDevice},{idTitulaire}")
    Call<Boolean> checkConfirmTitulaire(@Path("idDevice") String idDevice, @Path("idTitulaire") String idTitulaire);

    @GET("api/Equipements/ConfirmTitulaire/{idTitulaire}")
    Call<Boolean> checkEquipementAssigned(@Path("idTitulaire") String idTitulaire);

    @GET("api/Equipements/MultipleUsage/{idDevice},{idTitulaire}")
    Call<Boolean> checkMultipleUsage(@Path("idDevice") String idDevice, @Path("idTitulaire") String idTitulaire);

    @GET("api/Equipements/Check/{id}")
    Call<Equipements> getEquipementExist(@Path("{id} ") String id);

    @GET("api/Equipements/Check/{idDevice},{idTitulaire}")
    Call<Equipements> getEquipement(@Path("idDevice") String idDevice, @Path("idTitulaire") String idTitulaire);

    @PUT("api/Equipements/Update/{idDevice},{idTitulaire}")
    Call<Equipements> updateEquipement(@Path("idDevice") String idDevice, @Path("idTitulaire") String idTitulaire);

    /*
            Mises
         */
    @GET("api/Mises")
    Call<List<Mises>> listMise();

    @GET("api/Mises/{id}")
    Call<Mises> getMiseById(@Path("id") String id);

    @POST("api/Mises")
    Call<Mises> saveMise(@Body Mises mise);

    @PUT("api/Mises/{id}")
    Call<Mises> updateMise(@Path("id") String id, @Body Mises mise);

    @DELETE("api/Mises/{id}")
    Call<Mises> deleteMise(@Path("id") String id);

    @GET("api/Mises/UpdateAll")
    Call updateAllMise();

    @GET("api/Mises/MiseByCycle/{idCycle}")
    Call<Mises> getMiseByCycle(@Path("idCycle") String idCycle);

    @GET("api/Mises/RecoveredMiseByCycle/{idCycle}")
    Call<Mises> getRecoveredMiseByCycle(@Path("idCycle") String idCycle);

    @GET(" api/Mises/AmountByCycle/{idCycle}")
    Call<Integer> amountByCycleMises(@Path("idCycle") String idCycle);


    /*
            Titulaire
         */
    @GET("api/Titulaires")
    Call<List<Titulaires>> listTitulaire();

    @GET("api/Titulaires/{id}")
    Call<Titulaires> getTitulaireById(@Path("id") String id);

    @POST("api/Titulaires")
    Call<Titulaires> saveTitulaire(@Body Titulaires titulaire);

    @PUT("api/Titulaires/{id}")
    Call<Titulaires> updateTitulaire(@Path("id") String id, @Body Titulaires titulaire);

    @DELETE("api/Titulaires/{id}")
    Call<Titulaires> deleteTitulaire(@Path("id") String id);

    @GET("api/Titulaires/UpdateAll")
    Call updateAllTitulaire();

    /*
        Utilisateur
        */
    @GET("api/Utilisateurs")
    Call<List<Utilisateurs>> listUtilisateur();

    @GET("api/Utilisateurs/{id}")
    Call<Utilisateurs> getUtilisateurById(@Path("id") String id);

    @POST("api/Utilisateurs")
    Call<Utilisateurs> saveUtilisateur(@Body Utilisateurs utilisateur);

    @PUT("api/Utilisateurs/{id}")
    Call<Utilisateurs> updateUtilisateur(@Path("id") String id, @Body Utilisateurs utilisateur);

    @DELETE("api/Utilisateurs/{id}")
    Call<Utilisateurs> deleteUtilisateur(@Path("id") String id);

    @GET("api/Utilisateurs/UtilisateurByTitulaire/{idTitulaire}")
    Call<List<Utilisateurs>> getUtilisateurByTitulaire(@Path("idTitulaire") String idTitulaire);

    @GET("api/Utilisateurs/RecoveredUtilisateurByTitulaire/{idTitulaire}")
    Call<List<Utilisateurs>> getRecoveredUtilisateurByTitulaire(@Path("idTitulaire") String idTitulaire);

    @GET("api/Utilisateurs/UpdateAll")
    Call updateAllUtilisateur();

    /*
        Zones
        */
    @GET("api/Zones")
    Call<List<Zones>> listZones();

    @GET("api/Zones/{id}")
    Call<Zones> getZoneById(@Path("id") String id);

    @POST("api/Zones")
    Call<Zones> saveZone(@Body Zones zones);

    @PUT("api/Zones/{id}")
    Call<Zones> updateZone(@Path("id") String id, @Body Zones zones);

    @DELETE("api/Zones/{id}")
    Call<Zones> deleteZone(@Path("id") String id);

    /*
        Collecte
     */

    @GET("api/Collectes/CollectesByCycle/{idCycle}")
    Call<List<Collectes>> getCollecteByCycle(@Path("idCycle") String idCycle);

    @GET("api/Collectes/RecoveredCollectesByCycle/{idCycle}")
    Call<List<Collectes>> getCollecteRecoveredByCycle(@Path("idCycle") String idCycle);



    /*
        Equipements
     */

    @PUT("api/Equipements/{id}")
    Call<Equipements> updateEquipement(@Path("id") String id, @Body Equipements equipement);

    @PUT("api/Equipements/SetAuthorization/{id}")
    Call<Equipements> updateAutEquipements(@Path("id") Integer id, @Body Equipements equipement);

    @GET("api/Equipements/UnauthorizedDevices")
    Call<List<Equipements>> unauthorizedDevices();

    /*
            Mises
         */



    /*
       Produits
    */
    @GET("api/Produits")
    Call<List<Produits>> listProduits();

    /*
      Retraits
   */
    @GET("api/Retraits")
    Call<List<Retraits>> listRetraits();

    @POST("api/Retraits")
    Call<Retraits> saveRetraits(@Body Retraits retraits);
}
