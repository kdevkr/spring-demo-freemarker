<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>${title!"Html Email Template"}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
</head>
<body>
<!-- OUTERMOST CONTAINER TABLE -->
<table id="bodyTable" align="center" border="0" cellpadding="0" cellspacing="0" width="100%" style="border-collapse: collapse; background-color:#f8f8f8">
    <tr>
        <td style="padding-top: 20px; padding-bottom: 20px; ">
            <!-- 600px - 800px CONTENTS CONTAINER TABLE -->
            <table border="0" cellpadding="0" cellspacing="0" width="600" style="max-width:640px; margin:0 auto;">
                <tr>
                    <td align="center" bgcolor="#42b983" style="padding-top: 15px; padding-bottom: 15px;">
                        <h2 style="color: #efefef;">${title!"No Title"}</h2>
                    </td>
                </tr>
                <tr>
                    <td style="padding-top: 15px; padding-bottom: 15px; padding-left: 20px; padding-right: 20px;">
                        ${content!"Content Empty"}
                    </td>
                </tr>
                <tr>
                    <td style="text-align: center; font-size: 12px;">
                        <p style="border-top:solid 1px #eeeeee; font-size:1px; margin-top: 0; margin-bottom: 30px;"></p>
                        <p style="margin-bottom: 0;">This e-mail is only for outgoing calls.</p>
                        <p>Copyright © Mambo. All rights reserved.</p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>

</html>