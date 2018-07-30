<#macro page>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
    <style>

        body{
            background-color: #f6f6f6 !important;
        }
        #select{
            size: 35px !important;
        }
        pre {
            white-space: pre-wrap;
            overflow-x: auto;
        }
        #hider {
            position: absolute;
            top: -9999px;
            left: -9999px;
        }
        #hider:checked + .content {
            display: block;
        }
        #clickme {
            text-decoration: underline;
            color: blue;
            cursor: pointer;
        }

        .content {
            margin-top: 10px;
            display: none;
        }
        .morecontent pre {
            display: none;
        }
        .morelink {
            display: block;
        }
    </style>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
</head>
<body>
    <#nested>
<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-2.1.3.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.0/jquery.validate.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.13.0/additional-methods.min.js"></script>

</body>
</html>
</#macro>