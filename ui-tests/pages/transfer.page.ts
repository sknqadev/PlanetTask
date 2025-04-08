import { Page } from '@playwright/test';

export class TransferPage {
  constructor(readonly page: Page) {}

  readonly fromAccountInput = this.page.locator('#fromAccount');
  readonly toAccountInput = this.page.locator('#toAccount');
  readonly amountInput = this.page.locator('#amount');
  readonly currencySelect = this.page.locator('#currency');
  readonly submitButton = this.page.locator('button[type="submit"]');
  readonly confirmationMessage = this.page.locator('#confirmationMessage');
  readonly transactionId = this.page.locator('#transactionId');

  async goto() {
    await this.page.goto('http://localhost:5500/mock.html');
  }

  /**
   * Submits a fund transfer request from one account to another.
   *
   * @async
   * @param {string} fromAccount - The account number to transfer funds from.
   * @param {string} toAccount - The account number to transfer funds to.
   * @param {string} amount - The amount of money to transfer.
   * @param {string} currency - The currency type of the transfer.
   * @returns {Promise<void>} A promise that resolves when the transfer is submitted.
   */
  async submitTransfer(
    fromAccount: string,
    toAccount: string,
    amount: string,
    currency: string
  ): Promise<void> {
    await this.fromAccountInput.fill(fromAccount);
    await this.toAccountInput.fill(toAccount);
    await this.amountInput.fill(amount);
    await this.currencySelect.selectOption(currency);
    await this.submitButton.click();
  }
}
