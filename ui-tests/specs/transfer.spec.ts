import { test, expect } from '@playwright/test';
import { TransferPage } from '../pages/transfer.page';

test.describe('Banking Application', () => {
  let transferPage: TransferPage;

  test.beforeEach(async ({ page }) => {
    transferPage = new TransferPage(page);
    await transferPage.goto();
  });

  test('should load the home page', async () => {
    await expect(transferPage.page).toHaveTitle(/Online Banking/);
  });

  test('should perform a transfer', async () => {
    await transferPage.submitTransfer('123456', '654321', '100', 'USD');

    await expect(transferPage.confirmationMessage).toBeVisible();
    await expect(transferPage.transactionId).toBeVisible();
  });

  test('should show error for invalid transfer', async () => {
    await transferPage.submitTransfer('', '', '', 'USD');

    await expect(transferPage.confirmationMessage).toBeHidden();
    await expect(transferPage.transactionId).toBeHidden();
  });
});
