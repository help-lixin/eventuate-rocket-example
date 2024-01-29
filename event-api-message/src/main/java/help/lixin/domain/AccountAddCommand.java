package help.lixin.domain;

import io.eventuate.tram.commands.common.Command;

import java.math.BigDecimal;

public class AccountAddCommand implements Command {
    private BigDecimal money = new BigDecimal(0);

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
