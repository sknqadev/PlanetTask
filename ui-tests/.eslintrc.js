module.exports = {
    root: true,
    parser: '@typescript-eslint/parser',
    extends: [
        'eslint:recommended',
        'plugin:@typescript-eslint/recommended',
        'plugin:playwright/playwright-test',
        'prettier'
    ],
    plugins: ['@typescript-eslint'],
    env: {
        browser: true,
        node: true,
        es6: true,
    },
};
