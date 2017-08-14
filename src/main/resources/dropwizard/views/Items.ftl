<#-- @ftlvariable name="" type="dropwizard.views.ItemsView" -->
<#import "Layout.ftl" as layout>
<@layout.layout>
<h2 class="text-center">
    <small class="text-muted">DropWizard Bootstrap Pagination Example</small>
</h2>
<div class="container">
    <div class="table-responsive">

        <table class="table table-hover table-bordered table-sm">
            <thead class="thead-default">
            <tr>
                <th>#</th>
                <th>Name</th>
                <th>Description</th>
                <th>Next #</th>
                <th>Previous #</th>
            </tr>
            </thead>
            <tbody>
                <#list itemsRepresentation.items as item>
                <tr>
                    <th scope="row"><a href="${item.self.uri}">${item.id}</a></th>
                    <td>${item.name}</td>
                    <td>${item.description}</td>
                    <td>${item.nextId}</td>
                    <td>${item.prevId}</td>
                </tr>
                </#list>
            </tbody>
        </table>

        <nav aria-label="DropWizard Page navigation example">
            <ul class="pagination justify-content-end">
                <#assign count=itemsRepresentation.links?size>
                <#assign pages=itemsRepresentation.modelLimit / itemsRepresentation.limit>
                <#assign limit=itemsRepresentation.limit>

                <#list 1..pages as i>
                    <#assign offset= (i - 1) * limit>
                    <li class="page-item"><a class="page-link" href="/items/view?offset=${offset}&limit=10">${i}</a>
                    </li>
                </#list>


            </ul>
        </nav>

    </div>
</div>
</@layout.layout>
