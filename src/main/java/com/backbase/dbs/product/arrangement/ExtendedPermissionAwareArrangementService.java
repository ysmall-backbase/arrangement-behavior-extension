package com.backbase.dbs.product.arrangement;

import com.backbase.buildingblocks.backend.security.accesscontrol.accesscontrol.AccessControlValidator;
import com.backbase.dbs.product.Configurations;
import com.backbase.dbs.product.balance.BalanceService;
import com.backbase.dbs.product.clients.AccessControlClient;
import com.backbase.dbs.product.clients.JwtContext;
import com.backbase.dbs.product.common.clients.AccountOutboundWrapper;
import com.backbase.dbs.product.repository.ArrangementJpaRepository;
import com.backbase.dbs.product.repository.ProductJpaRepository;
import com.backbase.dbs.product.repository.arrangement.ArrangementSearchParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Extending getArrangementsByBusinessFunction operation.
 */
@Primary
@Service
public class ExtendedPermissionAwareArrangementService extends PermissionAwareArrangementService {

    private static final Logger LOG = LoggerFactory.getLogger(ExtendedPermissionAwareArrangementService.class);

    public ExtendedPermissionAwareArrangementService(
            Configurations configurations, ArrangementService arrangementService, JwtContext jwtContext,
            AccessControlClient accessControlClient, AccountOutboundWrapper accountOutboundWrapper,
            ArrangementUpdater arrangementUpdater, ArrangementJpaRepository arrangementJpaRepository,
            ProductJpaRepository productJpaRepository, BalanceService balanceService,
            AccessControlValidator accessControlValidator, DebitCardCommandMapper debitCardCommandMapper) {
        super(configurations, arrangementService, jwtContext, accessControlClient, accountOutboundWrapper,
                arrangementUpdater, arrangementJpaRepository, productJpaRepository,
                balanceService, accessControlValidator, debitCardCommandMapper);
        LOG.info("Extended service has been created");
    }

    @Override
    public GetArrangementsByBusinessFunctionResult getArrangementsByBusinessFunction(
            @NotNull ArrangementSearchParams arrangementSearchParams, Boolean withLatestBalances, String privilege,
            String[] businessFunction, String resourceName) {

        LOG.info("Pre hook");

        final GetArrangementsByBusinessFunctionResult result = super.getArrangementsByBusinessFunction(
                arrangementSearchParams, withLatestBalances, privilege, businessFunction, resourceName);

        LOG.info("Post hook");

        updateBalances(result);
        return result;
    }

    /**
     * Updates Balances.
     */
    private void updateBalances(GetArrangementsByBusinessFunctionResult result) {
        result.getArrangements().forEach(arrangement -> {
            //TODO: Mock data to be replaced with COCC account balances later.
            arrangement.setBookedBalance(getMockBalance());
            arrangement.setAvailableBalance(getMockBalance());
            arrangement.setMinimumRequiredBalance(getMockBalance());
            arrangement.setValueDateBalance(getMockBalance());
            LOG.info("Mock Balances Updated");
        });
    }

    private BigDecimal getMockBalance() {
        return BigDecimal.valueOf(Math.random()).multiply(new BigDecimal(100000))
                .setScale(2, RoundingMode.DOWN);
    }
}