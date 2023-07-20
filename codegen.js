module.exports = {
    schema: "https://api.musafira2z.com/graphql/",
    documents: ["src/api/**/*.graphql"],
    overwrite: true,
    generates: {
        "src/api/index.ts": {
            plugins: [
                "typescript",
                "typescript-operations",
                "typescript-react-apollo"
            ],
            config: {
                skipTypename: false,
                withHooks: true,
                withHOC: false,
                withComponent: false,
                apolloReactHooksImportFrom: "@apollo/client"
            }
        }
    }
};