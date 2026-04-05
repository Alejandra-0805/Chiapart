$files = Get-ChildItem -Path "app\src\main\java\com\alejandra\chiapart\features\auth" -Filter "*.kt" -Recurse
$oldPackage = "com.alejandra.amordepelis"
$newPackage = "com.alejandra.chiapart"

foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    if ($content -match $oldPackage) {
        $newContent = $content -replace [regex]::Escape($oldPackage), $newPackage
        Set-Content -Path $file.FullName -Value $newContent -NoNewline
        Write-Host "Updated: $($file.Name)"
    }
}

Write-Host "`nCompleted!"
