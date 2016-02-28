import com.weibei.client.Client
import com.weibei.client.blobvault.BlobVault
import com.weibei.client.transactions.TransactionManager
import com.weibei.client.transport.impl.JavaWebSocketTransportImpl
import com.weibei.core.types.known.tx.txns.Payment
import org.json.JSONObject

import static com.weibei.core.coretypes.AccountID.Destination

// Events
// Fields
import static com.weibei.core.coretypes.Amount.Amount

// run the main ;)
main()

void main() {
    def env  = System.getenv()
    // In fact we are using blobvault.weibei.com these days for the `old` style blob
    // so these are somewhat of a misnomer, albeit a misnomer replicated from the
    // client
    def user = env['PAYWARD_USER']
    def pass = env['PAYWARD_PASS']
    if (!user || user.empty || !pass || pass.empty) {
        println "Must sest PAYWARD_USER & PAYWARD_PASS environment variables"
        println "Tip: you can edit the run config with IntelliJ/Eclipse"
        System.exit(1)
    }

    new Client(new JavaWebSocketTransportImpl()).with {
        connect "wss://s1.weibei.com"
        on OnConnected, { _, client ->
            // Download test account blob
            def blob = getBlob(user, pass)
            def seed = blob.getString "master_seed"
            def ac = client.accountFromSeed seed
            makePayment ac.transactionManager(), 'rP1coskQzayaQ9geMdJgAV5f3tNZcHghzH', '1'
        }.rcurry(it)
    }
}

def JSONObject getBlob(def user, def pass) {
    def payward = new BlobVault("https://blobvault.weibei.com/");
    def env = System.getenv()
    payward.getBlob(user, pass)
}

def void makePayment(TransactionManager tm, def destination, def amt) {
    new Payment().with {
        // Set the fields
        put Destination, destination
        put Amount,      amt

        // Manage the transaction
        tm.manage(it).with {
            // Set event handlers
            once OnSubmitSuccess, { response ->
                println "Submit success response: ${response.engineResult()}"
            }
            once OnSubmitFailure, { response ->
                println "Submit failure response: ${response.engineResult()}"
                System.exit 0
            }
            once OnSubmitError, { response ->
                println "Submit error response: ${response.rpcerr}"
                System.exit 0
            }
            once OnTransactionValidated, { result ->
                println "Transaction finalized on ledger: ${result.ledgerIndex}"
                println "Transaction message ${result.message.toString(4)}"
                System.exit 0
            }
            // Queue it
            tm.queue it
        }
    }
}