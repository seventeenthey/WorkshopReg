/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.queensu.websvcs.workshopbooking.client.utility;

import ca.queensu.uis.services.email.ws.CaQueensuUisWebservicesEmailStub;
import ca.queensu.uis.services.email.ws.QueensEmailInterface;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

/**
 * <p>QueensEmailProducer class.</p>
 *
 * @author CISC-498
 * @version $Id: $Id
 */

@ApplicationScoped
@Default
public class QueensEmailProducer  implements Serializable{
    /**
     * <p>onPremEmailProd.</p>
     *
     * @return a {@link ca.queensu.uis.services.email.ws.QueensEmailInterface} object.
     */
    @Produces
    public QueensEmailInterface onPremEmailProd()
    {
        return new CaQueensuUisWebservicesEmailStub();
    }
    
}
