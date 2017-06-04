package services;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

public interface TransfersService {

    BigDecimal depositByEmail(@Nonnull String email, @Nonnull BigDecimal sum);

    BigDecimal withdrawByEmail(@Nonnull String email, @Nonnull BigDecimal sum);
}
